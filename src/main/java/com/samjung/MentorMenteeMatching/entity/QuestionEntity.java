package com.samjung.MentorMenteeMatching.entity;

import com.samjung.MentorMenteeMatching.dto.QuestionDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="question")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column
    private String title; //제목

    @Column(columnDefinition = "TEXT")
    private String content; // 내용

    @CreationTimestamp
    private LocalDateTime createdAt; // 생성일시

    private LocalDateTime modifyDate; //수정일시

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AnswerEntity> answerList; // 이 질문에 대한 답변 목록

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UsersEntity author; // 질문 작성자 (외래 키로 `users` 테이블 참조)

    @ManyToMany
    Set<UsersEntity> voter; //voter 값이 서로 중복되지 않도록 하기 위해

}
