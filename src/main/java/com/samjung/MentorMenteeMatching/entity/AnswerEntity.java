package com.samjung.MentorMenteeMatching.entity;

import com.samjung.MentorMenteeMatching.dto.AnswerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="answer")
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 답변 내용

    @CreationTimestamp
    private LocalDateTime createdAt; // 답변 생성일시

    private LocalDateTime modifyDate; // 답변 수정일시

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question; // 이 답변이 속한 질문 (외래 키로 `question` 테이블 참조)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UsersEntity author; // 이 답변의 작성자 (외래 키로 `users` 테이블 참조)

    @ManyToMany
    Set<UsersEntity> voter; //voter 값이 서로 중복되지 않도록 하기 위해

}
