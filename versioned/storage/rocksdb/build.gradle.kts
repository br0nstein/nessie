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

plugins {
  id("nessie-conventions-server")
  id("nessie-jacoco")
}

extra["maven.name"] = "Nessie - Storage - RocksDB"

description = "Storage implementation for RocksDB."

dependencies {
  implementation(project(":nessie-versioned-storage-common"))
  implementation(project(":nessie-versioned-storage-common-serialize"))

  // javax/jakarta
  compileOnly(libs.jakarta.validation.api)
  compileOnly(libs.javax.validation.api)
  compileOnly(libs.jakarta.annotation.api)
  compileOnly(libs.findbugs.jsr305)

  compileOnly(libs.errorprone.annotations)
  implementation(libs.guava)

  implementation(libs.rocksdb.jni)

  compileOnly(libs.immutables.builder)
  compileOnly(libs.immutables.value.annotations)
  annotationProcessor(libs.immutables.value.processor)

  compileOnly(project(":nessie-versioned-storage-testextension"))

  testImplementation(project(":nessie-versioned-storage-common-tests"))
  testImplementation(project(":nessie-versioned-storage-testextension"))
  testImplementation(project(":nessie-versioned-tests"))
  testImplementation(platform(libs.junit.bom))
  testImplementation(libs.bundles.junit.testing)
}
