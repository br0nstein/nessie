/*
 * Copyright (C) 2022 Dremio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.projectnessie.versioned.storage.common.logic;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static org.projectnessie.nessie.relocated.protobuf.ByteString.copyFromUtf8;
import static org.projectnessie.versioned.storage.common.indexes.StoreKey.key;
import static org.projectnessie.versioned.storage.common.logic.CommitConflict.ConflictType.KEY_EXISTS;
import static org.projectnessie.versioned.storage.common.logic.CommitRetry.commitRetry;
import static org.projectnessie.versioned.storage.common.logic.CreateCommit.Add.commitAdd;
import static org.projectnessie.versioned.storage.common.logic.CreateCommit.Remove.commitRemove;
import static org.projectnessie.versioned.storage.common.logic.CreateCommit.newCommitBuilder;
import static org.projectnessie.versioned.storage.common.logic.InternalRef.REF_REFS;
import static org.projectnessie.versioned.storage.common.logic.Logics.commitLogic;
import static org.projectnessie.versioned.storage.common.logic.Logics.indexesLogic;
import static org.projectnessie.versioned.storage.common.logic.PagingToken.emptyPagingToken;
import static org.projectnessie.versioned.storage.common.logic.PagingToken.pagingToken;
import static org.projectnessie.versioned.storage.common.logic.ReferenceLogicImpl.CommitReferenceResult.Kind.ADDED_TO_INDEX;
import static org.projectnessie.versioned.storage.common.logic.ReferenceLogicImpl.CommitReferenceResult.Kind.REF_ROW_EXISTS;
import static org.projectnessie.versioned.storage.common.logic.ReferenceLogicImpl.CommitReferenceResult.Kind.REF_ROW_MISSING;
import static org.projectnessie.versioned.storage.common.objtypes.CommitHeaders.newCommitHeaders;
import static org.projectnessie.versioned.storage.common.objtypes.RefObj.ref;
import static org.projectnessie.versioned.storage.common.persist.ObjId.EMPTY_OBJ_ID;
import static org.projectnessie.versioned.storage.common.persist.ObjType.COMMIT;
import static org.projectnessie.versioned.storage.common.persist.ObjType.REF;
import static org.projectnessie.versioned.storage.common.persist.Reference.INTERNAL_PREFIX;
import static org.projectnessie.versioned.storage.common.persist.Reference.isInternalReferenceName;
import static org.projectnessie.versioned.storage.common.persist.Reference.reference;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.AbstractIterator;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import org.projectnessie.nessie.relocated.protobuf.ByteString;
import org.projectnessie.versioned.storage.common.exceptions.CommitConflictException;
import org.projectnessie.versioned.storage.common.exceptions.CommitWrappedException;
import org.projectnessie.versioned.storage.common.exceptions.ObjNotFoundException;
import org.projectnessie.versioned.storage.common.exceptions.ObjTooLargeException;
import org.projectnessie.versioned.storage.common.exceptions.RefAlreadyExistsException;
import org.projectnessie.versioned.storage.common.exceptions.RefConditionFailedException;
import org.projectnessie.versioned.storage.common.exceptions.RefNotFoundException;
import org.projectnessie.versioned.storage.common.exceptions.RetryTimeoutException;
import org.projectnessie.versioned.storage.common.indexes.StoreIndex;
import org.projectnessie.versioned.storage.common.indexes.StoreIndexElement;
import org.projectnessie.versioned.storage.common.indexes.StoreKey;
import org.projectnessie.versioned.storage.common.logic.CommitRetry.RetryException;
import org.projectnessie.versioned.storage.common.objtypes.CommitObj;
import org.projectnessie.versioned.storage.common.objtypes.CommitOp;
import org.projectnessie.versioned.storage.common.objtypes.CommitOp.Action;
import org.projectnessie.versioned.storage.common.objtypes.CommitType;
import org.projectnessie.versioned.storage.common.objtypes.RefObj;
import org.projectnessie.versioned.storage.common.persist.ObjId;
import org.projectnessie.versioned.storage.common.persist.Persist;
import org.projectnessie.versioned.storage.common.persist.Reference;

/**
 * Named references management for non-transactional databases.
 *
 * <p>Provides the actual logic to access and maintain references, focused on non-transactional
 * databases. Transactional databases might get a simplified implementation that relies on the
 * database transactions.
 *
 * <p>Each reference is maintained in one database row. Updates are performed atomically, using CAS
 * operations when necessary.
 *
 * <p>A reference is considered as "created" and "not deleted", if it can be found via {@link
 * Persist#fetchReference(String)} and its {@link Reference#deleted() deleted flag} is {@code
 * false}. All other states, including the not-found state, are subject to recovery as described
 * below.
 *
 * <h3>Reference name index</h3>
 *
 * <p>Note that the {@link Persist low-level storage layer} does not provide a functionality to
 * maintain reference name indexes.
 *
 * <p>This logical layer on top of the storage layer maintains the set of reference names via the
 * internal, well known reference (via {@link InternalRef#REF_REFS}). Reference creations and
 * deletions are tracked in that internal reference using the exising mechanisms using commits and
 * {@link StoreIndex key-indexes}.
 *
 * <p>The logical approaches to create and delete a reference are designed to allow even
 * non-transactional databases to consistently delete a reference, where non-transactional databases
 * additionally run logic to re-run a previously failed process to create or delete a reference.
 *
 * <h3>Create reference / Logical approach</h3>
 *
 * <p>The <em>logical</em> approach to create a reference is as follows:
 *
 * <ol>
 *   <li>Create a commit in {@link InternalRef#REF_REFS} with a {@link Action#ADD} for the reference
 *       using a {@link RefObj} payload, which contains the reference information used during
 *       creation. Implementations must only commit, if the reference is not present, to allow the
 *       resume/recovery process described below.
 *   <li>After the reference has been added to {@link InternalRef#REF_REFS}, call {@link
 *       Persist#addReference(Reference)}, which creates the atomically updatable entry.
 * </ol>
 *
 * <p>Note: Updates to a named reference are <em>not</em> tracked in {@link InternalRef#REF_REFS}.
 *
 * <h3>Delete reference / Logical approach</h3>
 *
 * <ol>
 *   <li>The {@link Reference} is marked as {@link Reference#deleted()} using {@link
 *       Persist#markReferenceAsDeleted(Reference)}.
 *   <li>Then the reference deletion is tracked by creating a commit in {@link InternalRef#REF_REFS}
 *       using a {@link Action#REMOVE} operation. Implementations must only commit, if the reference
 *       is present, to allow the resume/recovery process described below.
 *   <li>Finally, the {@link Reference} is purged (row physically deleted) via {@link
 *       Persist#purgeReference(Reference)}.
 * </ol>
 *
 * <h3>Fetching references</h3>
 *
 * Accessing references by their full name is performed via {@link Persist#fetchReference(String)}
 * (or {@link Persist#fetchReferences(String[])}) and follows the resume/recovery process described
 * below.
 *
 * <h3>Listing references</h3>
 *
 * Listing/querying references is performed via the tip of {@link InternalRef#REF_REFS} and then
 * {@link Persist#fetchReferences(String[])} chunks of references to inquire their tips/HEADs.
 *
 * <h3>Non-transactional resume/recovery</h3>
 *
 * Transactional databases can safely use the native consistent update mechanisms provided by the
 * database. But non-transactional databases need some logic, which works as follows whenever a
 * reference is accessed:
 *
 * <ul>
 *   <li>{@link Persist#fetchReference(String)} (or {@link Persist#fetchReferences(String[])}) is
 *       used to fetch a reference by name.
 *   <li>If the reference is found and {@link Reference#deleted()} is {@code false}, the reference
 *       has been found and can be returned.
 *   <li>If the the {@link Reference#deleted()} flag is {@code true}, it is possible that a previous
 *       delete-reference operation failed in the middle. The implementation must resume the
 *       reference-deletion process described above.
 *   <li>If the reference has not been found via {@link Persist#fetchReference(String)} (or {@link
 *       Persist#fetchReferences(String[])}), check whether the reference name exists in {@link
 *       InternalRef#REF_REFS} and resume the create-reference operation described above.
 * </ul>
 *
 * <h3>Internal references</h3>
 *
 * {@link InternalRef Internal references} are neither visible nor can those be updated via this
 * {@link ReferenceLogicImpl}.
 */
final class ReferenceLogicImpl implements ReferenceLogic {

  private final Persist persist;

  ReferenceLogicImpl(Persist persist) {
    this.persist = persist;
  }

  @Override
  @Nonnull
  @jakarta.annotation.Nonnull
  public List<Reference> getReferences(
      @Nonnull @jakarta.annotation.Nonnull List<String> references) {
    Reference[] refs = persist.fetchReferences(references.toArray(new String[0]));
    List<Reference> r = new ArrayList<>(refs.length);

    Supplier<StoreIndex<CommitOp>> refsIndexSupplier = createRefsIndexSupplier();

    for (int i = 0; i < refs.length; i++) {
      Reference ref = refs[i];

      ref = maybeRecover(references.get(i), ref, refsIndexSupplier);

      if (ref != null && ref.name().startsWith(INTERNAL_PREFIX)) {
        // do not expose internal references
        ref = null;
      }
      r.add(ref);
    }
    return r;
  }

  @Override
  @Nonnull
  @jakarta.annotation.Nonnull
  public PagedResult<Reference, String> queryReferences(
      @Nonnull @jakarta.annotation.Nonnull ReferencesQuery referencesQuery) {
    Optional<PagingToken> pagingToken = referencesQuery.pagingToken();

    StoreKey prefix = referencesQuery.referencePrefix().map(StoreKey::keyFromString).orElse(null);

    StoreKey begin =
        pagingToken
            .map(PagingToken::token)
            .map(ByteString::toStringUtf8)
            .map(StoreKey::key)
            .orElse(prefix);

    StoreIndex<CommitOp> index = createRefsIndexSupplier().get();

    return new QueryIter(index, prefix, begin, referencesQuery.prefetch());
  }

  private final class QueryIter extends AbstractIterator<Reference>
      implements PagedResult<Reference, String> {
    private final StoreIndex<CommitOp> index;
    private final Iterator<StoreIndexElement<CommitOp>> base;
    private final StoreKey prefix;

    private QueryIter(
        StoreIndex<CommitOp> index, StoreKey prefix, StoreKey begin, boolean prefetch) {
      this.index = index;
      this.prefix = prefix;
      this.base = index.iterator(begin, null, prefetch);
    }

    @Override
    protected Reference computeNext() {
      while (true) {
        if (!base.hasNext()) {
          return endOfData();
        }

        StoreIndexElement<CommitOp> el = base.next();
        StoreKey k = el.key();
        if (prefix != null && !k.startsWith(prefix)) {
          return endOfData();
        }
        String name = k.rawString();
        Reference r = maybeRecover(name, persist.fetchReference(el.key().rawString()), () -> index);
        if (r != null) {
          return r;
        }
      }
    }

    @Nonnull
    @jakarta.annotation.Nonnull
    @Override
    public PagingToken tokenForKey(String key) {
      return key != null ? pagingToken(copyFromUtf8(key)) : emptyPagingToken();
    }
  }

  @Override
  @Nonnull
  @jakarta.annotation.Nonnull
  public Reference createReference(
      @Nonnull @jakarta.annotation.Nonnull String name,
      @Nonnull @jakarta.annotation.Nonnull ObjId pointer)
      throws RefAlreadyExistsException, RetryTimeoutException {
    checkArgument(!isInternalReferenceName(name));

    while (true) {
      CommitReferenceResult commitToIndex = commitCreateReference(name, pointer);
      Reference reference = commitToIndex.reference;

      switch (commitToIndex.kind) {
        case ADDED_TO_INDEX:
          checkState(!reference.deleted(), "internal error");
          try {
            return persist.addReference(reference);
          } catch (RefAlreadyExistsException e) {
            Reference existing = e.reference();
            if (existing != null && !existing.deleted()) {
              // Might happen in a rare race
              throw e;
            }
            // try again
            break;
          }
        case REF_ROW_MISSING:
          checkState(!reference.deleted(), "internal error");
          // Note: addReference() may or may not throw a ReferenceAlreadyExistsException
          reference = persist.addReference(reference);
          throw new RefAlreadyExistsException(reference);
        case REF_ROW_EXISTS:
          if (!reference.deleted()) {
            throw new RefAlreadyExistsException(reference);
          }
          maybeRecover(name, reference, createRefsIndexSupplier());
          // try again
          break;
        default:
          throw new IllegalStateException();
      }
    }
  }

  @Override
  public void deleteReference(
      @Nonnull @jakarta.annotation.Nonnull String name,
      @Nonnull @jakarta.annotation.Nonnull ObjId expectedPointer)
      throws RefNotFoundException, RefConditionFailedException, RetryTimeoutException {
    checkArgument(!isInternalReferenceName(name));

    Reference reference = persist.fetchReference(name);
    if (reference == null) {
      StoreKey nameKey = key(name);
      Supplier<StoreIndex<CommitOp>> indexSupplier = createRefsIndexSupplier();
      StoreIndexElement<CommitOp> index = indexSupplier.get().get(nameKey);
      if (index == null) {
        // not there --> okay
        throw new RefNotFoundException(reference(name, expectedPointer, false));
      }
      reference = maybeRecover(name, null, indexSupplier);

      if (reference == null) {
        // not there, even after recovery
        throw new RefNotFoundException(reference(name, expectedPointer, false));
      }
    }

    boolean actAsAlreadyDeleted = reference.deleted();

    if (!reference.pointer().equals(expectedPointer)) {
      // A previous deleteReference failed, act as if the first one succeeded, therefore this
      // one must throw a ReferenceNotFoundException instead of a ReferenceConditionFailedException
      if (!actAsAlreadyDeleted) {
        Supplier<StoreIndex<CommitOp>> indexSupplier = createRefsIndexSupplier();
        Reference recovered = maybeRecover(name, reference, indexSupplier);
        throw new RefConditionFailedException(recovered != null ? recovered : reference);
      }
    }

    if (!actAsAlreadyDeleted) {
      persist.markReferenceAsDeleted(reference);
    }

    commitDeleteReference(reference);

    persist.purgeReference(reference);

    if (actAsAlreadyDeleted) {
      // A previous deleteReference failed, act as if the first one succeeded, therefore this
      // one would have not found the reference.
      throw new RefNotFoundException(reference(name, expectedPointer, false));
    }
  }

  static final class CommitReferenceResult {
    final Reference reference;
    final Kind kind;

    CommitReferenceResult(Reference reference, Kind kind) {
      this.reference = reference;
      this.kind = kind;
    }

    enum Kind {
      ADDED_TO_INDEX,
      REF_ROW_EXISTS,
      REF_ROW_MISSING
    }
  }

  @VisibleForTesting // needed to simulate recovery scenarios
  CommitReferenceResult commitCreateReference(String name, ObjId pointer)
      throws RetryTimeoutException {
    try {
      return commitRetry(
          persist,
          (p, retryState) -> {
            Reference refRefs = requireNonNull(p.fetchReference(REF_REFS.name()));
            long created = p.config().currentTimeMicros();
            RefObj ref = ref(name, pointer, created);
            try {
              p.storeObj(ref);
            } catch (ObjTooLargeException e) {
              throw new RuntimeException(e);
            }

            StoreKey k = key(name);

            Instant now = persist.config().clock().instant();
            CreateCommit c =
                newCommitBuilder()
                    .message("Create reference " + name + " pointing to " + pointer)
                    .parentCommitId(refRefs.pointer())
                    .addAdds(commitAdd(k, 0, requireNonNull(ref.id()), null, null))
                    .headers(
                        newCommitHeaders()
                            .add("operation", "create")
                            .add("name", name)
                            .add("head", pointer.toString())
                            .add("timestamp", now.toString())
                            .add("timestamp.millis", Long.toString(now.toEpochMilli()))
                            .build())
                    .commitType(CommitType.INTERNAL)
                    .build();

            commitReferenceChange(p, refRefs, c);

            return new CommitReferenceResult(reference(name, pointer, false), ADDED_TO_INDEX);
          });
    } catch (CommitConflictException e) {
      checkState(e.conflicts().size() == 1, "Unexpected amount of conflicts %s", e.conflicts());

      CommitConflict conflict = e.conflicts().get(0);
      checkState(conflict.conflictType() == KEY_EXISTS, "Unexpected conflict type %s", conflict);

      Supplier<StoreIndex<CommitOp>> indexSupplier = createRefsIndexSupplier();
      StoreIndexElement<CommitOp> el = indexSupplier.get().get(key(name));
      checkNotNull(el, "Key %s missing in index", name);

      Reference existing = persist.fetchReference(name);

      if (existing != null) {
        return new CommitReferenceResult(existing, REF_ROW_EXISTS);
      }

      RefObj ref;
      try {
        ref =
            persist.fetchTypedObj(
                requireNonNull(el.content().value(), "Reference commit operation has no value"),
                REF,
                RefObj.class);
      } catch (ObjNotFoundException ex) {
        throw new RuntimeException("Internal error getting reference creation object", e);
      }
      return new CommitReferenceResult(
          reference(name, ref.initialPointer(), false), REF_ROW_MISSING);
    } catch (CommitWrappedException e) {
      throw new RuntimeException(
          format(
              "An unexpected internal error happened while committing the creation of the reference '%s'",
              reference(name, pointer, false)),
          e.getCause());
    }
  }

  @VisibleForTesting // needed to simulate recovery scenarios
  // Note: commitForReference is for testing, to test race conditions
  void commitDeleteReference(Reference reference) throws RetryTimeoutException {
    try {
      commitRetry(
          persist,
          (p, retryState) -> {
            Reference refRefs = requireNonNull(p.fetchReference(REF_REFS.name()));
            CommitObj commit;
            try {
              commit = p.fetchTypedObj(refRefs.pointer(), COMMIT, CommitObj.class);
            } catch (ObjNotFoundException e) {
              throw new RuntimeException("Internal error getting reference creation log commit", e);
            }
            StoreIndex<CommitOp> index =
                indexesLogic(persist).incrementalIndexForUpdate(commit, Optional.empty());

            StoreKey key = key(reference.name());

            StoreIndexElement<CommitOp> indexElement = index.get(key);
            if (indexElement != null) {
              CommitOp indexElementContent = indexElement.content();
              if (indexElementContent.action().exists()) {
                Instant now = persist.config().clock().instant();
                CreateCommit c =
                    newCommitBuilder()
                        .message(
                            "Drop reference "
                                + reference.name()
                                + " pointing to "
                                + reference.pointer())
                        .parentCommitId(refRefs.pointer())
                        .addRemoves(
                            commitRemove(
                                key,
                                0,
                                requireNonNull(indexElementContent.value()),
                                indexElementContent.contentId()))
                        .headers(
                            newCommitHeaders()
                                .add("operation", "delete")
                                .add("name", reference.name())
                                .add("head", reference.pointer().toString())
                                .add("timestamp", now.toString())
                                .add("timestamp.millis", Long.toString(now.toEpochMilli()))
                                .build())
                        .commitType(CommitType.INTERNAL)
                        .build();

                commitReferenceChange(p, refRefs, c);
              }
            }

            return null;
          });
    } catch (CommitConflictException e) {
      throw new RuntimeException(
          format(
              "An unexpected internal error happened while committing the deletion of the reference '%s'",
              reference),
          e);
    } catch (CommitWrappedException e) {
      throw new RuntimeException(
          format(
              "An unexpected internal error happened while committing the deletion of the reference '%s'",
              reference),
          e.getCause());
    }
  }

  private static void commitReferenceChange(Persist p, Reference refRefs, CreateCommit c)
      throws CommitConflictException, RetryException {
    CommitObj commit;
    try {
      commit = commitLogic(p).doCommit(c, emptyList());
    } catch (ObjNotFoundException e) {
      throw new RuntimeException("Internal error committing to log of references", e);
    }

    checkState(commit != null);

    // Commit to REF_REFS
    try {
      p.updateReferencePointer(refRefs, commit.id());
    } catch (RefConditionFailedException e) {
      throw new RetryException();
    } catch (RefNotFoundException e) {
      throw new RuntimeException("Internal reference not found", e);
    }
  }

  @Override
  @Nonnull
  @jakarta.annotation.Nonnull
  public Reference assignReference(
      @Nonnull @jakarta.annotation.Nonnull Reference current,
      @Nonnull @jakarta.annotation.Nonnull ObjId newPointer)
      throws RefNotFoundException, RefConditionFailedException {
    checkArgument(!current.isInternal());

    return persist.updateReferencePointer(current, newPointer);
  }

  private Reference maybeRecover(
      @Nonnull @jakarta.annotation.Nonnull String name,
      Reference ref,
      @Nonnull @jakarta.annotation.Nonnull Supplier<StoreIndex<CommitOp>> refsIndexSupplier) {
    if (ref == null) {
      StoreIndexElement<CommitOp> commitOp = refsIndexSupplier.get().get(key(name));

      if (commitOp == null) {
        // Reference not in index - nothing to do.
        return null;
      }

      CommitOp commitOpContent = commitOp.content();
      if (commitOpContent.action().exists()) {
        // The reference is present in InternalRefs.REF_REFS as an existing key, but not via
        // Persist.findReference --> resume reference creation.
        RefObj initialRef;
        try {
          initialRef =
              persist.fetchTypedObj(
                  requireNonNull(
                      commitOpContent.value(), "Reference commit operation has no value"),
                  REF,
                  RefObj.class);
        } catch (ObjNotFoundException e) {
          throw new RuntimeException("Internal error getting reference creation object", e);
        }
        ref = reference(name, initialRef.initialPointer(), false);
        try {
          ref = persist.addReference(ref);
        } catch (RefAlreadyExistsException e) {
          ref = e.reference();
        }
        return ref;
      } else {
        // Reference committed to int/refs as deleted, it's gone
        return null;
      }

    } else if (ref.deleted()) {
      StoreIndexElement<CommitOp> commitOp = refsIndexSupplier.get().get(key(name));

      if (commitOp == null) {
        throw new RuntimeException("Loaded Reference is marked as deleted, but not found in index");
      }

      CommitOp commitOpContent = commitOp.content();
      if (commitOpContent.action().exists()) {
        try {
          commitDeleteReference(ref);
          persist.purgeReference(ref);
        } catch (RefNotFoundException e) {
          // ignore
        } catch (RetryTimeoutException | RefConditionFailedException e) {
          throw new RuntimeException(e);
        }
      } else {
        // Reference marked as deleted in index, purge it.
        try {
          persist.purgeReference(ref);
        } catch (RefNotFoundException e) {
          // ignore
        } catch (RefConditionFailedException e) {
          throw new RuntimeException(e);
        }
      }
      return null;
    }

    return ref;
  }

  @VisibleForTesting
  Supplier<StoreIndex<CommitOp>> createRefsIndexSupplier() {
    return indexesLogic(persist)
        .createIndexSupplier(
            () -> {
              Reference ref = persist.fetchReference(REF_REFS.name());
              return ref != null ? ref.pointer() : EMPTY_OBJ_ID;
            });
  }
}
