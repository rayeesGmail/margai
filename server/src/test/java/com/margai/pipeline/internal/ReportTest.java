package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.common.api.IstClock;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * The run report (TECH_PLAN §6.3): its markdown shape, the SHA-256 that pins the input, the
 * {@code <date>-<command>.md} file name on the IST day, and the overwrite on a same-day re-run.
 */
class ReportTest {

    /** 2026-09-12 04:30 UTC is 10:00 IST on the same day. */
    static final IstClock CLOCK = new IstClock(Clock.fixed(Instant.parse("2026-09-12T04:30:00Z"), ZoneOffset.UTC));

    @TempDir
    Path dir;

    @Test
    void rendersTheRunTheInputAndTheSections() throws IOException {
        Path input = Files.writeString(dir.resolve("taxonomy.csv"), "abc");

        Report report = new Report("margai-pipeline taxonomy load", input)
                .read("516 nodes")
                .section("syllabus_nodes")
                .table(List.of("inserted", "updated"), List.of(List.of("516", "0")))
                .section("orphans")
                .list(List.of());
        String text = report.render(CLOCK.nowIst());

        assertThat(report.slug()).isEqualTo("taxonomy-load");
        assertThat(text).startsWith("# margai-pipeline taxonomy load\n\n- run: 2026-09-12 10:00 IST\n- input: " + input.toAbsolutePath().normalize()
                + "\n- sha256: ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad\n- read: 516 nodes\n- result: ok\n");
        assertThat(text).contains("\n## syllabus_nodes\n\n| inserted | updated |\n|---|---|\n| 516 | 0 |\n");
        assertThat(text).endsWith("## orphans\n\nnone\n");
    }

    @Test
    void aFailedRunKeepsItsReasonAndAListPrintsItsItems() throws IOException {
        Path input = Files.writeString(dir.resolve("prerequisites.csv"), "x");

        String text = new Report("margai-pipeline taxonomy prerequisites", input)
                .read("2 edges")
                .failed("the prerequisite graph has a cycle among [A, B]")
                .section("orphan edges")
                .list(List.of("A -> B", "C -> D"))
                .render(CLOCK.nowIst());

        assertThat(text).contains("- result: FAILED: the prerequisite graph has a cycle among [A, B]\n");
        assertThat(text).endsWith("## orphan edges\n\n- A -> B\n- C -> D\n");
    }

    @Test
    void writesTheDatedFileAndOverwritesItOnASameDayRerun() throws IOException {
        Path input = Files.writeString(dir.resolve("cutoffs.csv"), "x");
        Path reports = dir.resolve("reports/nested");
        Reports writer = new Reports(CLOCK);

        Reports.Written first = writer.write(reports, new Report("margai-pipeline cutoffs load", input).read("40 rows"));
        Reports.Written second = writer.write(reports, new Report("margai-pipeline cutoffs load", input).read("41 rows"));

        assertThat(first.path()).isEqualTo(reports.resolve("2026-09-12-cutoffs-load.md"));
        assertThat(second.path()).isEqualTo(first.path());
        assertThat(Files.readString(first.path())).isEqualTo(second.text()).contains("- read: 41 rows");
        assertThat(Files.list(reports)).hasSize(1);
    }
}
