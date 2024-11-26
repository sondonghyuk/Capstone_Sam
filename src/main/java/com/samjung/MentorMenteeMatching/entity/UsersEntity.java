package com.samjung.MentorMenteeMatching.entity;

import com.samjung.MentorMenteeMatching.dto.UsersDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.samjung.MentorMenteeMatching.entity.UserType;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId; // 유저의 고유 식별자, 자동 증가되는 기본 키

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType; // 유저 타입 (MENTOR 또는 MENTEE), Enum 타입으로 저장

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username; // 사용자 이름, 최대 길이 50자, 중복 불가

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email; // 이메일 주소, 최대 길이 100자, 중복 불가

    @Column(name = "password", length = 100, nullable = false)
    private String password; // 사용자 비밀번호, 최대 길이 100자

    @Transient
    private String confirmPassword; // 비밀번호 확인용 필드, 데이터베이스에 저장되지 않음

    @Column(name = "phone_number", length = 30, nullable = false)
    private String phoneNumber; // 전화번호, 최대 길이 30자

    @Column(name = "department", length = 100, nullable = false)
    private String department; // 학과 정보, 최대 길이 100자

    @Column(name = "student_id", length = 50, nullable = false, unique = true)
    private String studentId; // 학번, 최대 길이 50자, 중복 불가

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 계정 생성일, 자동 생성, 수정 불가

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 계정 정보 수정일, 자동 업데이트

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<QuestionEntity> questions; // 사용자가 작성한 질문 목록

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AnswerEntity> answers; // 사용자가 작성한 답변 목록
}



