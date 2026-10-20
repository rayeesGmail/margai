package com.margai.curriculum.internal;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface QuestionTopicRepository extends JpaRepository<QuestionTopic, QuestionTopicId> {

    List<QuestionTopic> findByIdQuestionIdIn(Collection<UUID> questionIds);
}
