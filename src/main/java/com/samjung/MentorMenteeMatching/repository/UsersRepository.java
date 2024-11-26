package com.samjung.MentorMenteeMatching.repository;

import com.samjung.MentorMenteeMatching.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Long> {
    //이메일로 회원 정보 조회(select * from users_table where users_id=?)
    Optional<UsersEntity> findByEmail(String usersEmail);

    //사용자 ID로 User 엔티티를 조회
    Optional<UsersEntity> findByUsername(String username);
}
