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
package org.projectnessie.versioned.persist.mongodb;

import com.mongodb.client.MongoClient;
import javax.annotation.Nullable;
import org.immutables.value.Value;
import org.projectnessie.versioned.persist.adapter.DatabaseConnectionConfig;

@Value.Immutable
public interface MongoClientConfig extends DatabaseConnectionConfig {

  static MongoClientConfig of(MongoClient client) {
    return ImmutableMongoClientConfig.builder().client(client).build();
  }

  @Nullable
  @jakarta.annotation.Nullable
  String getConnectionString();

  MongoClientConfig withConnectionString(String connectionString);

  @Nullable
  @jakarta.annotation.Nullable
  String getDatabaseName();

  MongoClientConfig withDatabaseName(String databaseName);

  @Nullable
  @jakarta.annotation.Nullable
  MongoClient getClient();

  MongoClientConfig withClient(MongoClient client);
}
