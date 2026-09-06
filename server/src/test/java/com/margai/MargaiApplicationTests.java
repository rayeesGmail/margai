package com.margai;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

/**
 * D2 boot proof: the application context starts against a real Postgres 18 + pgvector
 * container (same image as docker-compose.yml), Flyway runs, and the health endpoint
 * reports the database component UP. From D4 this is also the proof that every JPA entity
 * matches its migration ({@code ddl-auto: validate}) and that, without the {@code local} or
 * {@code test} profile, the seed taxonomy is absent (DECISIONS D3.20: production never sees it).
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class MargaiApplicationTests {

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void healthReportsUpWithDatabaseComponent() {
        MvcTestResult result = mvc.get().uri("/actuator/health").exchange();

        assertThat(result).hasStatusOk();
        assertThat(result).bodyJson().extractingPath("$.status").isEqualTo("UP");
        assertThat(result).bodyJson().extractingPath("$.components.db.status").isEqualTo("UP");
    }

    @Test
    void seedTaxonomyIsAbsentWithoutTheLocalOrTestProfile() {
        Integer nodeCount = jdbc.queryForObject("SELECT count(*) FROM syllabus_nodes", Integer.class);

        assertThat(nodeCount).isZero();
    }
}
