package com.margai;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * One PostgreSQL 18 + pgvector container per test JVM (TECH_PLAN §8.1 "singleton container
 * pattern"), same image as docker-compose.yml. It is started in the static initialiser and is
 * deliberately not a Spring bean: Spring Boot stops container beans when a context closes, and a
 * context that fails to start closes at once, which would take the shared container down with it.
 * Testcontainers' reaper removes it when the JVM exits.
 *
 * <p>Each set of active profiles gets its own database inside the container ({@code margai_default},
 * {@code margai_test}, …) because the profiles differ in Flyway locations: a database that has seen
 * the {@code db/seed} repeatable migration would fail validation in a context that does not
 * configure that location. Tests that need an empty schema of their own create a further database
 * through {@link #createDatabase(String)}.
 */
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    public static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer(
            DockerImageName.parse("pgvector/pgvector:pg18").asCompatibleSubstituteFor("postgres"));

    private static final Set<String> CREATED = new HashSet<>();

    static {
        POSTGRES.start();
    }

    @Bean
    DynamicPropertyRegistrar postgresProperties(Environment environment) {
        String database = databaseFor(environment.getActiveProfiles());
        createDatabase(database);
        return registry -> {
            registry.add("spring.datasource.url", () -> jdbcUrl(database));
            registry.add("spring.datasource.username", POSTGRES::getUsername);
            registry.add("spring.datasource.password", POSTGRES::getPassword);
        };
    }

    /** JDBC URL of a database inside the shared container. */
    public static String jdbcUrl(String database) {
        return "jdbc:postgresql://" + POSTGRES.getHost() + ":"
                + POSTGRES.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT) + "/" + database;
    }

    /** Creates the database if it does not exist yet; safe to call repeatedly. */
    public static synchronized void createDatabase(String database) {
        if (CREATED.contains(database)) {
            return;
        }
        try (Connection admin = adminConnection()) {
            boolean exists;
            try (PreparedStatement query = admin.prepareStatement("SELECT 1 FROM pg_database WHERE datname = ?")) {
                query.setString(1, database);
                try (ResultSet rows = query.executeQuery()) {
                    exists = rows.next();
                }
            }
            if (!exists) {
                try (Statement statement = admin.createStatement()) {
                    statement.execute("CREATE DATABASE " + database);
                }
            }
            CREATED.add(database);
        } catch (SQLException e) {
            throw new IllegalStateException("could not create test database " + database, e);
        }
    }

    /** Drops a database created by {@link #createDatabase(String)}, closing its sessions. */
    public static synchronized void dropDatabase(String database) {
        try (Connection admin = adminConnection(); Statement statement = admin.createStatement()) {
            statement.execute("DROP DATABASE IF EXISTS " + database + " WITH (FORCE)");
            CREATED.remove(database);
        } catch (SQLException e) {
            throw new IllegalStateException("could not drop test database " + database, e);
        }
    }

    public static Connection adminConnection() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }

    private static String databaseFor(String[] activeProfiles) {
        if (activeProfiles.length == 0) {
            return "margai_default";
        }
        String[] sorted = activeProfiles.clone();
        Arrays.sort(sorted);
        return "margai_" + String.join("_", sorted);
    }
}
