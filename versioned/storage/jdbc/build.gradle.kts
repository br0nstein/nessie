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

import org.apache.tools.ant.taskdefs.condition.Os

plugins {
  id("nessie-conventions-server")
  id("nessie-jacoco")
}

extra["maven.name"] = "Nessie - Storage - JDBC"

description = "Storage implementation for JDBC, supports H2, PostgreSQL and CockroachDB."

dependencies {
  implementation(project(":nessie-versioned-storage-common"))
  implementation(project(":nessie-versioned-storage-common-proto"))

  // javax/jakarta
  compileOnly(libs.jakarta.validation.api)
  compileOnly(libs.javax.validation.api)
  compileOnly(libs.jakarta.annotation.api)
  compileOnly(libs.findbugs.jsr305)

  compileOnly(libs.errorprone.annotations)
  implementation(libs.guava)
  implementation(libs.agrona)
  implementation(libs.slf4j.api)

  compileOnly(libs.immutables.builder)
  compileOnly(libs.immutables.value.annotations)
  annotationProcessor(libs.immutables.value.processor)

  compileOnly(libs.agroal.pool)
  compileOnly(libs.h2)
  compileOnly(libs.postgresql)
  compileOnly(libs.testcontainers.postgresql)
  compileOnly(libs.testcontainers.cockroachdb)
  compileOnly(libs.docker.java.api)

  compileOnly(project(":nessie-versioned-storage-testextension"))

  testFixturesApi(project(":nessie-versioned-storage-common"))
  testFixturesApi(project(":nessie-versioned-storage-common-tests"))
  testFixturesApi(project(":nessie-versioned-storage-testextension"))
  testFixturesApi(project(":nessie-versioned-tests"))
  testFixturesImplementation(libs.agroal.pool)
  testRuntimeOnly(libs.h2)
  intTestRuntimeOnly(libs.postgresql)
  intTestRuntimeOnly(libs.testcontainers.postgresql)
  intTestRuntimeOnly(libs.testcontainers.cockroachdb)
  intTestRuntimeOnly(libs.docker.java.api)
  testFixturesImplementation(platform(libs.junit.bom))
  testFixturesImplementation(libs.bundles.junit.testing)
}

// Testcontainers is not supported on Windows :(
if (Os.isFamily(Os.FAMILY_WINDOWS)) {
  tasks.named<Test>("intTest").configure { this.enabled = false }
}

// Issue w/ testcontainers/podman in GH workflows :(
if (Os.isFamily(Os.FAMILY_MAC) && System.getenv("CI") != null) {
  tasks.named<Test>("intTest") { this.enabled = false }
}
