package com.samjung.MentorMenteeMatching.repository;

import com.samjung.MentorMenteeMatching.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity,Long> {
    QuestionEntity findByTitle(String title);
    QuestionEntity findByTitleAndContent(String title, String content);
    List<QuestionEntity> findByTitleLike(String title);
    //페이징 구현
    Page<QuestionEntity> findAll(Pageable pageable);

    Page<QuestionEntity> findAll(Specification<QuestionEntity> spec,Pageable pageable);

    // 제목 또는 내용에 검색어가 포함된 질문을 찾는 쿼리 메소드
    Page<QuestionEntity> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<QuestionEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}
