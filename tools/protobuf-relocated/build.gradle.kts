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

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  alias(libs.plugins.nessie.reflectionconfig)
  id("nessie-conventions-server")
  id("nessie-shadow-jar")
}

extra["maven.name"] = "Nessie - Relocated Protobuf ${libs.protobuf.java.get().version}"

dependencies { compileOnly(libs.protobuf.java) }

reflectionConfig {
  // Consider classes that extend one of these classes...
  classExtendsPatterns.set(
    listOf(
      "com.google.protobuf.GeneratedMessageV3",
      "com.google.protobuf.GeneratedMessageV3.Builder"
    )
  )
  // ... and classes the implement this interface.
  classImplementsPatterns.set(listOf("com.google.protobuf.ProtocolMessageEnum"))
  // Include the "com.google.protobuf:protobuf-java" dependency.
  includeConfigurations.set(listOf("compileClasspath"))
  // Relocate it to our Nessie package name.
  relocations.put("com[.]google[.]protobuf[.]", "org.projectnessie.nessie.relocated.protobuf.$1")
}

val shadowJar =
  tasks.named<ShadowJar>("shadowJar") {
    relocate("com.google.protobuf", "org.projectnessie.nessie.relocated.protobuf")
    manifest {
      attributes["Specification-Title"] = "Google Protobuf"
      attributes["Specification-Version"] = libs.protobuf.java.get().version
    }
    configurations = listOf(project.configurations.getByName("compileClasspath"))
    dependencies { include(dependency(libs.protobuf.java.get())) }
  }

tasks.named("compileJava") { finalizedBy(shadowJar) }

tasks.named("processResources") { finalizedBy(shadowJar) }

tasks.named<Jar>("jar") { dependsOn("processJandexIndex", "generateReflectionConfig") }
