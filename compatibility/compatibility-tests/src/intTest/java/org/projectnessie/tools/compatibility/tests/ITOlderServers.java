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
package org.projectnessie.tools.compatibility.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.projectnessie.model.NessieConfiguration;
import org.projectnessie.tools.compatibility.api.Version;
import org.projectnessie.tools.compatibility.api.VersionCondition;
import org.projectnessie.tools.compatibility.internal.OlderNessieServersExtension;

@ExtendWith(OlderNessieServersExtension.class)
public class ITOlderServers extends AbstractCompatibilityTests {

  @Override
  Version getClientVersion() {
    return Version.CURRENT;
  }

  @Test
  @Override
  void getConfigV1() {
    NessieConfiguration config = api.getConfig();
    assertThat(config.getDefaultBranch()).isEqualTo("main");
    assertThat(config.getMinSupportedApiVersion()).isEqualTo(1);
    if (version.isLessThan(Version.API_V2)) {
      assertThat(config.getMaxSupportedApiVersion()).isEqualTo(1);
    } else {
      assertThat(config.getMaxSupportedApiVersion()).isEqualTo(2);
    }
    assertThat(config.getActualApiVersion()).isEqualTo(0);
    assertThat(config.getSpecVersion()).isNull();
  }

  @Test
  @VersionCondition(minVersion = "0.59.0")
  @Override
  void getConfigV2() {
    NessieConfiguration config = apiV2.getConfig();
    assertThat(config.getDefaultBranch()).isEqualTo("main");
    assertThat(config.getMinSupportedApiVersion()).isEqualTo(1);
    assertThat(config.getMaxSupportedApiVersion()).isEqualTo(2);
    assertThat(config.getActualApiVersion()).isEqualTo(2);
    assertThat(config.getSpecVersion()).isEqualTo("2.0.0");
  }
}
