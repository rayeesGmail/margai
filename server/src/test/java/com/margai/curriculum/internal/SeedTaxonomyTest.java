package com.margai.curriculum.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.common.api.AttemptType;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.TrackPhase;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * TECH_PLAN §8.2 second test: the {@code db/seed} location loads the D4 test taxonomy under the
 * {@code test} profile — two subjects, six chapters, two topics, an acyclic prerequisite graph,
 * one archetype track and three synthetic cutoffs (§2.9). Read through the entities so the
 * seeded rows also prove the enum and column mappings.
 */
@SpringBootTest
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
class SeedTaxonomyTest {

    @Autowired
    private SyllabusNodeRepository nodes;

    @Autowired
    private SyllabusPrerequisiteRepository prerequisites;

    @Autowired
    private ArchetypeTrackRepository tracks;

    @Autowired
    private ArchetypeTrackStepRepository steps;

    @Autowired
    private CutoffRepository cutoffs;

    @Test
    void treeHasTwoSubjectsSixChaptersAndTwoTopics() {
        assertThat(nodes.findByKindOrderBySortOrder(NodeKind.subject)).extracting(SyllabusNode::getCode)
                .containsExactly("PHY", "CHE");
        assertThat(nodes.findByKindOrderBySortOrder(NodeKind.unit)).hasSize(2);
        assertThat(nodes.findByKindOrderBySortOrder(NodeKind.chapter)).extracting(SyllabusNode::getCode)
                .containsExactlyInAnyOrder("PHY.11.KIN", "PHY.11.NLM", "PHY.11.ROT",
                        "CHE.11.MOLE", "CHE.11.ATOM", "CHE.11.THERMO");
        assertThat(nodes.findByKindOrderBySortOrder(NodeKind.topic)).extracting(SyllabusNode::getCode)
                .containsExactly("PHY.11.ROT.TORQUE", "PHY.11.ROT.MOI");

        SyllabusNode rotation = nodes.findByCode("PHY.11.ROT").orElseThrow();
        SyllabusNode mechanics = nodes.findByCode("PHY.11.MECH").orElseThrow();
        assertThat(rotation.getParentId()).isEqualTo(mechanics.getId());
        assertThat(rotation.getClassLevel()).isEqualTo((short) 11);
        assertThat(rotation.getDefaultLearnMinutes()).isEqualTo(420);
        assertThat(rotation.getNameHi()).isNotBlank();
        assertThat(rotation.isNeetRelevant()).isTrue();
    }

    @Test
    void prerequisiteGraphHasFourEdgesAndNoCycle() {
        List<SyllabusPrerequisite> edges = prerequisites.findAll();
        assertThat(edges).hasSize(4);

        Map<UUID, List<UUID>> next = new HashMap<>();
        for (SyllabusPrerequisite edge : edges) {
            next.computeIfAbsent(edge.getFromNodeId(), k -> new java.util.ArrayList<>()).add(edge.getToNodeId());
        }
        Set<UUID> done = new HashSet<>();
        for (UUID start : next.keySet()) {
            assertThat(reachesItself(start, start, next, new HashSet<>(), done))
                    .as("cycle through %s", start).isFalse();
        }

        UUID kinematics = nodes.findByCode("PHY.11.KIN").orElseThrow().getId();
        UUID laws = nodes.findByCode("PHY.11.NLM").orElseThrow().getId();
        assertThat(next.get(kinematics)).containsExactly(laws);
    }

    @Test
    void dropperTrackHasSixLearnStepsThenAMock() {
        ArchetypeTrack dropper = tracks.findByCode(AttemptType.dropper).orElseThrow();
        assertThat(tracks.count()).isEqualTo(1);
        assertThat(dropper.getWeeks()).isEqualTo((short) 7);

        List<ArchetypeTrackStep> ordered = steps.findByTrackIdOrderBySequence(dropper.getId());
        assertThat(ordered).hasSize(7);
        assertThat(ordered).extracting(ArchetypeTrackStep::getSequence).containsExactly(1, 2, 3, 4, 5, 6, 7);
        assertThat(ordered.subList(0, 6)).allSatisfy(step -> assertThat(step.getPhase()).isEqualTo(TrackPhase.learn));
        assertThat(ordered.get(6).getPhase()).isEqualTo(TrackPhase.mock);
        assertThat(ordered.get(6).getNodeId()).isEqualTo(nodes.findByCode("PHY").orElseThrow().getId());
    }

    @Test
    void cutoffsAreThreeSyntheticRows() {
        List<Cutoff> all = cutoffs.findAll();
        assertThat(all).hasSize(3);
        assertThat(all).allSatisfy(cutoff -> {
            assertThat(cutoff.getYear()).isEqualTo((short) 2025);
            assertThat(cutoff.getQuotaScope()).isEqualTo("AIQ");
            assertThat(cutoff.getSource()).contains("synthetic");
        });
    }

    private static boolean reachesItself(UUID start, UUID current, Map<UUID, List<UUID>> next,
            Set<UUID> path, Set<UUID> done) {
        if (!path.add(current)) {
            return true;
        }
        for (UUID target : next.getOrDefault(current, List.of())) {
            if (target.equals(start) || (!done.contains(target) && reachesItself(start, target, next, path, done))) {
                return true;
            }
        }
        path.remove(current);
        done.add(current);
        return false;
    }
}
