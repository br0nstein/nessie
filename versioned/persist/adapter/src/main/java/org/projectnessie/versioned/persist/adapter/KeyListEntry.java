/*
 * Copyright (C) 2020 Dremio
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
package org.projectnessie.versioned.persist.adapter;

import javax.annotation.Nullable;
import org.immutables.value.Value;
import org.projectnessie.model.ContentKey;
import org.projectnessie.versioned.Hash;

/** Composite of key, content-id, content-type and commit-id. */
@Value.Immutable(lazyhash = true) // this type is used as a map-key in an expensive test
public interface KeyListEntry {
  ContentKey getKey();

  ContentId getContentId();

  byte getPayload();

  @Nullable
  @jakarta.annotation.Nullable
  Hash getCommitId();

  static KeyListEntry of(ContentKey key, ContentId contentId, byte payload, Hash commitId) {
    ImmutableKeyListEntry.Builder builder =
        ImmutableKeyListEntry.builder().key(key).payload(payload).contentId(contentId);
    if (commitId != null) {
      builder.commitId(commitId);
    }
    return builder.build();
  }
}
