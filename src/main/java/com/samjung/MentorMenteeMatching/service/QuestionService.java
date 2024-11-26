package com.samjung.MentorMenteeMatching.service;

import com.samjung.MentorMenteeMatching.dto.QuestionDto;
import com.samjung.MentorMenteeMatching.entity.AnswerEntity;
import com.samjung.MentorMenteeMatching.entity.QuestionEntity;
//import com.samjung.MentorMenteeMatching.entity.UsersEntity;
import com.samjung.MentorMenteeMatching.entity.UsersEntity;
import com.samjung.MentorMenteeMatching.repository.QuestionRepository;
//import com.samjung.MentorMenteeMatching.repository.UsersRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {
    private final QuestionRepository questionRepository;

    //제목 검색기능
    private Specification<QuestionEntity> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<QuestionEntity> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                // 제목에만 검색어 포함
                return cb.like(q.get("title"), "%" + kw + "%");  // 제목만 검색
            }
        };
    }

    //질문 목록 데이터를 조회해 리턴(정수 타입의 페이지 번호를 입력받음)
    public Page<QuestionEntity> getList(int page,String kw){
        //등록한 순서로 페이징
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));

        // 제목만 포함된 질문을 검색하는 Specification 사용
        Specification<QuestionEntity> spec = search(kw);

        // Repository에서 findAll(spec, pageable)로 조건에 맞는 데이터를 반환
        return this.questionRepository.findAll(spec, pageable);
    }

    // 특정 질문 조회
    public QuestionEntity getQuestion(Long id){
        return this.questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));
    }

    //질문과 내용을 입력받아 질문으로 저장
    public void create(String title, String content, UsersEntity user){
        QuestionEntity q = new QuestionEntity();
        q.setTitle(title);
        q.setContent(content);
        q.setCreatedAt(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }
    //수정
    public void modify(QuestionEntity question,String title,String content){
        question.setTitle(title);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    //질문 삭제
    public void delete(QuestionEntity question){
        this.questionRepository.delete(question);
    }

    //추천인 저장
    public void vote(QuestionEntity question, UsersEntity user){
        question.getVoter().add(user);
        this.questionRepository.save(question);
    }

    // 검색 기능 처리
    public Page<QuestionEntity> searchQuestions(String kw, Pageable pageable) {
        if (kw == null || kw.isEmpty()) {
            return questionRepository.findAll(pageable); // 검색어가 없으면 모든 질문 반환
        } else {
            return questionRepository.findByTitleContainingOrContentContaining(kw, kw, pageable);
            // 제목 또는 내용에 검색어가 포함된 질문을 반환
        }
    }
}
