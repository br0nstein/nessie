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
package org.projectnessie.model.types;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.immutables.value.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.projectnessie.model.Content;
import org.projectnessie.model.types.ContentTypes.RegistryHelper;

@ExtendWith(SoftAssertionsExtension.class)
public class TestRegistryHelper {
  @InjectSoftAssertions protected SoftAssertions soft;

  @Test
  void badRegistrations() {
    RegistryHelper registryHelper = new RegistryHelper();

    soft.assertThatIllegalArgumentException()
        .isThrownBy(() -> registryHelper.register(Content.class))
        .withMessage(
            "Content-type registration: org.projectnessie.model.Content has no @JsonTypeName annotation");

    soft.assertThatIllegalArgumentException()
        .isThrownBy(() -> registryHelper.register(RegistryHelperNoJsonTypeName.class))
        .withMessage(
            "Content-type registration: org.projectnessie.model.types.TestRegistryHelper$RegistryHelperNoJsonTypeName has no @JsonTypeName annotation");

    soft.assertThatIllegalArgumentException()
        .isThrownBy(() -> registryHelper.register(RegistryHelperIllegalName.class))
        .withMessage(
            "Illegal content-type registration: illegal name ' ILLEGAL ' for org.projectnessie.model.types.TestRegistryHelper$RegistryHelperIllegalName");

    soft.assertThatCode(() -> registryHelper.register(RegistryHelperGood.class))
        .doesNotThrowAnyException();
    soft.assertThatIllegalStateException()
        .isThrownBy(() -> registryHelper.register(RegistryHelperDupe.class))
        .withMessage(
            "Duplicate content type registration for DUPE/org.projectnessie.model.types.TestRegistryHelper$RegistryHelperDupe, existing: DUPE/org.projectnessie.model.types.TestRegistryHelper$RegistryHelperGood");
  }

  @Test
  void getUnknown() {
    soft.assertThatIllegalArgumentException()
        .isThrownBy(() -> ContentTypes.forName("NO_NO_NOT_THERE"))
        .withMessage("No content type registered for name NO_NO_NOT_THERE");
  }

  @Value.Immutable
  public abstract static class RegistryHelperNoJsonTypeName extends Content {
    @Override
    public Type getType() {
      return ContentTypes.forName("JSON_TYPE_NAME");
    }
  }

  @Value.Immutable
  @JsonTypeName(" ILLEGAL ")
  public abstract static class RegistryHelperIllegalName extends Content {
    @Override
    public Type getType() {
      return ContentTypes.forName(" ILLEGAL ");
    }
  }

  @Value.Immutable
  @JsonTypeName("JSON_TYPE_NAME")
  public abstract static class RegistryHelperNameMismatch extends Content {
    @Override
    public Type getType() {
      return ContentTypes.forName("JSON_TYPE_NAME");
    }
  }

  @Value.Immutable
  @JsonTypeName("DUPE")
  public abstract static class RegistryHelperGood extends Content {
    @Override
    public Type getType() {
      return ContentTypes.forName("DUPE");
    }
  }

  @Value.Immutable
  @JsonTypeName("DUPE")
  public abstract static class RegistryHelperDupe extends Content {
    @Override
    public Type getType() {
      return ContentTypes.forName("DUPE");
    }
  }
}
