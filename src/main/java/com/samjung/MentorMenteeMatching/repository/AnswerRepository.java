package com.samjung.MentorMenteeMatching.repository;

import com.samjung.MentorMenteeMatching.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity,Long> {
//    List<AnswerEntity> findByQuestionEntityQuestionId(Long questionId);

}
