package com.margai.curriculum.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.common.api.AttemptType;
import com.margai.common.api.Category;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.SeatType;
import com.margai.curriculum.api.Subject;
import com.margai.curriculum.api.TrackPhase;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Repository slice (TECH_PLAN §8.1): migration V3 applies and its constraints hold — unique
 * {@code code}, "a subject node has no parent", no self-prerequisite, unique step sequence per
 * track, and the cutoff natural key.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
class CurriculumConstraintsTest {

    private static final Short CLASS_11 = 11;

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
    void nodeDefaultsMatchTheMigration() {
        SyllabusNode physics = nodes.saveAndFlush(subject("PHY"));

        assertThat(physics.getWeightageMarksAvg()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(physics.isNeetRelevant()).isTrue();
        assertThat(nodes.findByCode("PHY")).isPresent();
        assertThat(nodes.findByKindOrderBySortOrder(NodeKind.subject)).extracting(SyllabusNode::getCode)
                .containsExactly("PHY");
    }

    @Test
    void codeIsUnique() {
        nodes.saveAndFlush(subject("CHE"));

        assertThatThrownBy(() -> nodes.saveAndFlush(subject("CHE")))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("syllabus_nodes_code_key");
    }

    @Test
    void subjectNodeCannotHaveAParent() {
        SyllabusNode physics = nodes.saveAndFlush(subject("PHY"));
        SyllabusNode rooted = new SyllabusNode("ZOO", Subject.zoology, null, physics.getId(),
                NodeKind.subject, "Zoology", 4);

        assertThatThrownBy(() -> nodes.saveAndFlush(rooted))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("syllabus_nodes_subject_root_check");
    }

    @Test
    void prerequisiteRejectsASelfEdge() {
        SyllabusNode physics = nodes.saveAndFlush(subject("PHY"));
        SyllabusNode kinematics = nodes.saveAndFlush(chapter("PHY.11.KIN", physics.getId(), 1));
        SyllabusNode laws = nodes.saveAndFlush(chapter("PHY.11.NLM", physics.getId(), 2));

        SyllabusPrerequisite edge = prerequisites.saveAndFlush(
                new SyllabusPrerequisite(kinematics.getId(), laws.getId()));
        assertThat(edge.getFromNodeId()).isEqualTo(kinematics.getId());
        assertThat(edge.getToNodeId()).isEqualTo(laws.getId());

        assertThatThrownBy(() -> prerequisites.saveAndFlush(
                new SyllabusPrerequisite(kinematics.getId(), kinematics.getId())))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("syllabus_prerequisites_from_to_check");
    }

    @Test
    void stepSequenceIsUniquePerTrack() {
        SyllabusNode physics = nodes.saveAndFlush(subject("PHY"));
        ArchetypeTrack dropper = tracks.saveAndFlush(new ArchetypeTrack(AttemptType.dropper, "Dropper", (short) 7));
        steps.saveAndFlush(new ArchetypeTrackStep(dropper.getId(), physics.getId(), 1, TrackPhase.learn, (short) 1));

        assertThat(tracks.findByCode(AttemptType.dropper)).isPresent();
        assertThat(steps.findByTrackIdOrderBySequence(dropper.getId())).hasSize(1);
        assertThatThrownBy(() -> steps.saveAndFlush(
                new ArchetypeTrackStep(dropper.getId(), physics.getId(), 1, TrackPhase.mock, (short) 2)))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("archetype_track_steps_track_id_sequence_key");
    }

    @Test
    void cutoffNaturalKeyIsUnique() {
        cutoffs.saveAndFlush(new Cutoff((short) 2025, Category.general, "AIQ", SeatType.govt_mbbs, (short) 500, "test"));

        assertThatThrownBy(() -> cutoffs.saveAndFlush(
                new Cutoff((short) 2025, Category.general, "AIQ", SeatType.govt_mbbs, (short) 510, "test")))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("cutoffs_year_category_quota_scope_seat_type_key");
    }

    private static SyllabusNode subject(String code) {
        return new SyllabusNode(code, Subject.physics, null, null, NodeKind.subject, code, 1);
    }

    private static SyllabusNode chapter(String code, UUID parentId, int sortOrder) {
        return new SyllabusNode(code, Subject.physics, CLASS_11, parentId, NodeKind.chapter, code, sortOrder);
    }
}
