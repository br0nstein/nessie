/*
 * Copyright (C) 2023 Dremio
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
package org.projectnessie.server.error;

import static org.mockito.ArgumentMatchers.any;

import javax.enterprise.context.RequestScoped;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintDefinitionException;
import javax.validation.GroupDefinitionException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.mockito.Mockito;
import org.projectnessie.error.NessieNotFoundException;
import org.projectnessie.error.NessieReferenceNotFoundException;
import org.projectnessie.model.CommitMeta;
import org.projectnessie.versioned.BackendLimitExceededException;
import org.projectnessie.versioned.GetNamedRefsParams;
import org.projectnessie.versioned.ReferenceInfo;
import org.projectnessie.versioned.ReferenceNotFoundException;
import org.projectnessie.versioned.paging.PaginationIterator;
import org.projectnessie.versioned.storage.common.persist.Persist;
import org.projectnessie.versioned.storage.versionstore.VersionStoreImpl;

/** REST service used to generate a bunch of violations for {@link TestNessieError}. */
@RequestScoped
@Path("/nessieErrorTest")
@Consumes(MediaType.WILDCARD)
@Produces(MediaType.APPLICATION_JSON)
public class ErrorTestService {

  @Path("nullParameterQueryGet")
  @GET
  public String nullParameterQueryGet(@NotNull @QueryParam("hash") String hash) {
    return "oh oh";
  }

  @Path("nullParameterQueryPost")
  @POST
  public String nullParameterQueryPost(@NotNull @QueryParam("hash") String hash) {
    return "oh oh";
  }

  @Path("emptyParameterQueryGet")
  @GET
  public String emptyParameterQueryGet(@NotEmpty @QueryParam("hash") String hash) {
    return "oh oh";
  }

  @Path("blankParameterQueryGet")
  @GET
  public String blankParameterQueryGet(@NotBlank @QueryParam("hash") String hash) {
    return "oh oh";
  }

  @Path("unsupportedMediaTypePut")
  @PUT
  @Consumes(MediaType.TEXT_PLAIN)
  public String unsupportedMediaTypePut(@NotNull String txt) {
    return "oh oh";
  }

  @Path("nessieNotFound")
  @GET
  public String nessieNotFound() throws NessieNotFoundException {
    throw new NessieReferenceNotFoundException(
        "not-there-message", new Exception("not-there-exception"));
  }

  @Path("basicEntity")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public String basicEntity(@Valid SomeEntity entity) {
    return "oh oh";
  }

  // Triggers the "else-ish" part in ResteasyExceptionMapper.toResponse()
  @Path("constraintDefinitionException")
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  public String constraintDefinitionException() {
    throw new ConstraintDefinitionException("meep");
  }

  // Triggers the "else-ish" part in ResteasyExceptionMapper.toResponse()
  @Path("constraintDeclarationException")
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  public String constraintDeclarationException() {
    throw new ConstraintDeclarationException("meep");
  }

  // Triggers the "else-ish" part in ResteasyExceptionMapper.toResponse()
  @Path("groupDefinitionException")
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  public String groupDefinitionException() {
    throw new GroupDefinitionException("meep");
  }

  /**
   * Throws an exception depending on the parameter.
   *
   * @return nothing
   */
  @Path("unhandledExceptionInTvsStore/{exception}")
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  public String unhandledExceptionInTvsStore(@PathParam("exception") String exception)
      throws ReferenceNotFoundException {
    Exception ex;
    switch (exception) {
      case "runtime":
        ex = new RuntimeException("Store.getValues-throwing");
        break;
      case "throttle":
        ex = new BackendLimitExceededException("Store.getValues-throttled");
        break;
      default:
        throw new IllegalArgumentException("test code error");
    }

    Persist persist = Mockito.mock(Persist.class);
    Mockito.when(persist.fetchReference(any())).thenThrow(ex);

    VersionStoreImpl tvs = new VersionStoreImpl(persist);
    try (PaginationIterator<ReferenceInfo<CommitMeta>> refs =
        tvs.getNamedRefs(GetNamedRefsParams.DEFAULT, null)) {
      refs.forEachRemaining(ref -> {});
    }
    return "we should not get here";
  }
}
