package com.margai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * TECH_PLAN §8.2 (PLAN D4 ✅ "migrations reversible"): start from an empty database, migrate to
 * latest, execute every migration's undo newest first — {@code rollback/U<n>__*.sql} when present,
 * else the {@code -- ROLLBACK:} … {@code -- END ROLLBACK} block in its header — and assert that
 * only {@code flyway_schema_history} remains. A migration without an undo fails the test. The
 * migrated schema is also checked for PostgreSQL enum types, which DECISIONS D3.5 forbids.
 */
class MigrationReversibilityTest {

    private static final String ROLLBACK_START = "-- ROLLBACK:";
    private static final String ROLLBACK_END = "-- END ROLLBACK";

    private final PathMatchingResourcePatternResolver resources = new PathMatchingResourcePatternResolver();

    @Test
    void everyMigrationUndoesItself() throws Exception {
        String database = "reversibility_" + Long.toUnsignedString(System.nanoTime(), 36);
        TestcontainersConfiguration.createDatabase(database);
        String url = TestcontainersConfiguration.jdbcUrl(database);
        String user = TestcontainersConfiguration.POSTGRES.getUsername();
        String password = TestcontainersConfiguration.POSTGRES.getPassword();

        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(url, user, password)
                    .locations("classpath:db/migration")
                    .load();
            MigrateResult migrated = flyway.migrate();
            assertThat(migrated.migrationsExecuted).as("migrations applied").isGreaterThan(0);

            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                assertThat(query(connection,
                        "SELECT typname FROM pg_type WHERE typnamespace = 'public'::regnamespace AND typtype = 'e'"))
                        .as("PostgreSQL enum types (DECISIONS D3.5 forbids them)").isEmpty();

                for (Resource migration : migrationsNewestFirst()) {
                    for (String sql : undoStatements(migration)) {
                        try (Statement statement = connection.createStatement()) {
                            statement.execute(sql);
                        }
                    }
                }

                assertThat(query(connection, "SELECT tablename FROM pg_tables WHERE schemaname = 'public'"))
                        .as("tables left after rollback").containsExactly("flyway_schema_history");
                assertThat(query(connection, "SELECT relname FROM pg_class WHERE relnamespace = 'public'::regnamespace"
                        + " AND relkind IN ('S', 'v', 'm')"))
                        .as("sequences and views left after rollback").isEmpty();
                assertThat(query(connection, "SELECT extname FROM pg_extension"))
                        .as("extensions left after rollback").containsExactly("plpgsql");
            }
        } finally {
            TestcontainersConfiguration.dropDatabase(database);
        }
    }

    private List<Resource> migrationsNewestFirst() throws IOException {
        List<Resource> migrations = new ArrayList<>(Arrays.asList(
                resources.getResources("classpath:db/migration/V*.sql")));
        migrations.sort(Comparator.comparingInt(MigrationReversibilityTest::version).reversed());
        return migrations;
    }

    private List<String> undoStatements(Resource migration) throws IOException {
        int version = version(migration);
        // classpath*: tolerates the rollback directory not existing yet (no non-trivial undo so far).
        Resource[] undoFiles = resources.getResources("classpath*:db/rollback/U" + version + "__*.sql");
        String undo = undoFiles.length > 0
                ? undoFiles[0].getContentAsString(StandardCharsets.UTF_8)
                : rollbackBlock(migration);
        List<String> statements = new ArrayList<>();
        for (String statement : undo.split(";")) {
            if (!statement.isBlank()) {
                statements.add(statement.trim());
            }
        }
        if (statements.isEmpty()) {
            fail("%s has no undo: add a -- ROLLBACK: block or a rollback/U%d__*.sql file",
                    migration.getFilename(), version);
        }
        return statements;
    }

    private static String rollbackBlock(Resource migration) throws IOException {
        StringBuilder block = new StringBuilder();
        boolean inside = false;
        for (String line : migration.getContentAsString(StandardCharsets.UTF_8).split("\n")) {
            String trimmed = line.strip();
            if (trimmed.equals(ROLLBACK_START)) {
                inside = true;
            } else if (trimmed.equals(ROLLBACK_END)) {
                break;
            } else if (inside && trimmed.startsWith("--")) {
                block.append(trimmed.substring(2).strip()).append('\n');
            }
        }
        return block.toString();
    }

    private static int version(Resource migration) {
        String name = migration.getFilename();
        return Integer.parseInt(name.substring(1, name.indexOf("__")));
    }

    private static List<String> query(Connection connection, String sql) throws SQLException {
        List<String> values = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet rows = statement.executeQuery(sql)) {
            while (rows.next()) {
                values.add(rows.getString(1));
            }
        }
        return values;
    }
}
