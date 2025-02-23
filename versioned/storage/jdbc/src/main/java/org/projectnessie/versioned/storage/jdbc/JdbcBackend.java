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
package org.projectnessie.versioned.storage.jdbc;

import static com.google.common.base.Preconditions.checkState;
import static org.projectnessie.versioned.storage.jdbc.AbstractJdbcPersist.sqlSelectMultiple;
import static org.projectnessie.versioned.storage.jdbc.JdbcColumnType.NAME;
import static org.projectnessie.versioned.storage.jdbc.JdbcColumnType.OBJ_ID;
import static org.projectnessie.versioned.storage.jdbc.SqlConstants.COLS_OBJS_ALL;
import static org.projectnessie.versioned.storage.jdbc.SqlConstants.COL_OBJ_ID;
import static org.projectnessie.versioned.storage.jdbc.SqlConstants.COL_REFS_DELETED;
import static org.projectnessie.versioned.storage.jdbc.SqlConstants.COL_REFS_NAME;
import static org.projectnessie.versioned.storage.jdbc.SqlConstants.COL_REFS_POINTER;
import static org.projectnessie.versioned.storage.jdbc.SqlConstants.COL_REPO_ID;
import static org.projectnessie.versioned.storage.jdbc.SqlConstants.CREATE_TABLE_OBJS;
import static org.projectnessie.versioned.storage.jdbc.SqlConstants.CREATE_TABLE_REFS;
import static org.projectnessie.versioned.storage.jdbc.SqlConstants.ERASE_OBJS;
import static org.projectnessie.versioned.storage.jdbc.SqlConstants.ERASE_REFS;
import static org.projectnessie.versioned.storage.jdbc.SqlConstants.TABLE_OBJS;
import static org.projectnessie.versioned.storage.jdbc.SqlConstants.TABLE_REFS;

import com.google.common.collect.ImmutableMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.sql.DataSource;
import org.projectnessie.versioned.storage.common.persist.Backend;
import org.projectnessie.versioned.storage.common.persist.PersistFactory;

final class JdbcBackend implements Backend {

  private final DatabaseSpecific databaseSpecific;
  private final DataSource dataSource;
  private final boolean closeDataSource;
  private final JdbcBackendConfig config;

  JdbcBackend(
      @Nonnull @jakarta.annotation.Nonnull JdbcBackendConfig config,
      @Nonnull @jakarta.annotation.Nonnull DatabaseSpecific databaseSpecific,
      boolean closeDataSource) {
    this.config = config;
    this.dataSource = config.dataSource();
    this.databaseSpecific = databaseSpecific;
    this.closeDataSource = closeDataSource;
  }

  static RuntimeException unhandledSQLException(SQLException e) {
    return new RuntimeException("Unhandled SQL exception", e);
  }

  DatabaseSpecific databaseSpecific() {
    return databaseSpecific;
  }

  @Override
  public void close() {
    if (closeDataSource) {
      try {
        if (dataSource instanceof AutoCloseable) {
          ((AutoCloseable) dataSource).close();
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Nonnull
  @jakarta.annotation.Nonnull
  @Override
  public PersistFactory createFactory() {
    return new JdbcPersistFactory(this);
  }

  Connection borrowConnection() throws SQLException {
    Connection c = dataSource.getConnection();
    c.setAutoCommit(false);
    return c;
  }

  @Override
  public void setupSchema() {
    try (Connection conn = borrowConnection()) {
      Integer nameTypeId = databaseSpecific.columnTypeIds().get(NAME);
      Integer objIdTypeId = databaseSpecific.columnTypeIds().get(OBJ_ID);
      createTableIfNotExists(
          conn,
          TABLE_REFS,
          CREATE_TABLE_REFS,
          Stream.of(COL_REPO_ID, COL_REFS_NAME, COL_REFS_POINTER, COL_REFS_DELETED)
              .collect(Collectors.toSet()),
          ImmutableMap.of(COL_REPO_ID, nameTypeId, COL_REFS_NAME, nameTypeId));
      createTableIfNotExists(
          conn,
          TABLE_OBJS,
          CREATE_TABLE_OBJS,
          Stream.concat(
                  Stream.of(COL_REPO_ID), Arrays.stream(COLS_OBJS_ALL.split(",")).map(String::trim))
              .collect(Collectors.toSet()),
          ImmutableMap.of(COL_REPO_ID, nameTypeId, COL_OBJ_ID, objIdTypeId));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void createTableIfNotExists(
      Connection conn,
      String tableName,
      String createTable,
      Set<String> expectedColumns,
      Map<String, Integer> expectedPrimaryKey)
      throws SQLException {
    Map<JdbcColumnType, String> columnTypesMap = databaseSpecific.columnTypes();
    Object[] types =
        Arrays.stream(JdbcColumnType.values()).map(columnTypesMap::get).toArray(Object[]::new);

    // TODO implement catalog + schema stuff...
    String catalog = config.catalog();
    String schema = config.schema();

    createTable = MessageFormat.format(createTable, types);

    try (Statement st = conn.createStatement()) {
      if (conn.getMetaData().storesLowerCaseIdentifiers()) {
        tableName = tableName.toLowerCase(Locale.ROOT);
      } else if (conn.getMetaData().storesUpperCaseIdentifiers()) {
        tableName = tableName.toUpperCase(Locale.ROOT);
      }

      try (ResultSet rs = conn.getMetaData().getTables(catalog, schema, tableName, null)) {
        if (rs.next()) {
          Map<String, Integer> primaryKey = new LinkedHashMap<>();
          Map<String, Integer> columns = new LinkedHashMap<>();

          // table already exists
          try (ResultSet cols = conn.getMetaData().getColumns(catalog, schema, tableName, null)) {
            while (cols.next()) {
              String colName = cols.getString("COLUMN_NAME").toLowerCase(Locale.ROOT);
              columns.put(colName, cols.getInt("DATA_TYPE"));
            }
          }
          try (ResultSet cols = conn.getMetaData().getPrimaryKeys(catalog, schema, tableName)) {
            while (cols.next()) {
              String colName = cols.getString("COLUMN_NAME").toLowerCase(Locale.ROOT);
              int colType = columns.get(colName);
              primaryKey.put(colName.toLowerCase(Locale.ROOT), colType);
            }
          }

          checkState(
              primaryKey.equals(expectedPrimaryKey),
              "Expected primary key columns %s do not match existing primary key columns %s for table '%s'. DDL template:\n%s",
              expectedPrimaryKey.keySet(),
              primaryKey.keySet(),
              tableName,
              createTable);
          checkState(
              columns.keySet().containsAll(expectedColumns),
              "Expected columns %s do not match the existing columns %s for table '%s'. DDL template:\n%s",
              expectedColumns,
              columns.keySet(),
              tableName,
              createTable);

          // Existing table looks compatible
          return;
        }
      }

      st.executeUpdate(createTable);
    }
  }

  @Override
  public String configInfo() {
    StringBuilder info = new StringBuilder();
    String s = config.catalog();
    if (s != null && !s.isEmpty()) {
      info.append("catalog: ").append(s);
    }
    s = config.schema();
    if (s != null && !s.isEmpty()) {
      if (info.length() > 0) {
        info.append(", ");
      }
      info.append("schema: ").append(s);
    }
    return info.toString();
  }

  @Override
  public void eraseRepositories(Set<String> repositoryIds) {
    if (repositoryIds == null || repositoryIds.isEmpty()) {
      return;
    }

    try (Connection conn = borrowConnection()) {

      try (PreparedStatement ps =
          conn.prepareStatement(sqlSelectMultiple(ERASE_REFS, repositoryIds.size()))) {
        int i = 1;
        for (String repositoryId : repositoryIds) {
          ps.setString(i++, repositoryId);
        }
        ps.executeUpdate();
      }
      try (PreparedStatement ps =
          conn.prepareStatement(sqlSelectMultiple(ERASE_OBJS, repositoryIds.size()))) {
        int i = 1;
        for (String repositoryId : repositoryIds) {
          ps.setString(i++, repositoryId);
        }
        ps.executeUpdate();
      }
      conn.commit();
    } catch (SQLException e) {
      throw unhandledSQLException(e);
    }
  }
}
