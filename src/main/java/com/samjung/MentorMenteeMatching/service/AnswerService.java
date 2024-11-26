package com.samjung.MentorMenteeMatching.service;

import com.samjung.MentorMenteeMatching.dto.AnswerDto;
import com.samjung.MentorMenteeMatching.entity.AnswerEntity;
import com.samjung.MentorMenteeMatching.entity.QuestionEntity;
import com.samjung.MentorMenteeMatching.entity.UsersEntity;
import com.samjung.MentorMenteeMatching.repository.AnswerRepository;
import com.samjung.MentorMenteeMatching.repository.QuestionRepository;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    //답변 생성
    public AnswerEntity create(QuestionEntity question, String content, UsersEntity author){
        AnswerEntity answer = new AnswerEntity();
        answer.setContent(content);
        answer.setCreatedAt(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
        return answer;
    }

    //답변 조회
    public AnswerEntity getAnswer(Long id){
        Optional<AnswerEntity> answer = this.answerRepository.findById(id);
        if(answer.isPresent()){
            return answer.get();
        }else{
            throw new IllegalArgumentException("answer not found");
        }
    }
    //답변 수정
    public void modify(AnswerEntity answer,String content){
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }
    //답변 삭제
    public void delete(AnswerEntity answer){
        this.answerRepository.delete(answer);
    }

    //답변 추첮
    public void vote(AnswerEntity answer , UsersEntity user){
        answer.getVoter().add(user);
        this.answerRepository.save(answer);
    }
}
