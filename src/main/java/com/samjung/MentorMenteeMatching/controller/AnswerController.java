package com.samjung.MentorMenteeMatching.controller;

import com.samjung.MentorMenteeMatching.dto.AnswerDto;
import com.samjung.MentorMenteeMatching.entity.AnswerEntity;
import com.samjung.MentorMenteeMatching.entity.QuestionEntity;
import com.samjung.MentorMenteeMatching.entity.UsersEntity;
import com.samjung.MentorMenteeMatching.service.AnswerService;
import com.samjung.MentorMenteeMatching.service.QuestionService;
import com.samjung.MentorMenteeMatching.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/answer")
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UsersService usersService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Long id,
                               @Valid AnswerDto answerDto, BindingResult bindingResult, Principal principal){
        QuestionEntity question = this.questionService.getQuestion(id);
        UsersEntity user = this.usersService.getUser(principal.getName());
        //검증에 실패할 경우 다시 답변 등록
        if(bindingResult.hasErrors()){
            model.addAttribute("question",question);
            return "question/detail";
        }
        AnswerEntity answer = this.answerService.create(question,answerDto.getContent(),user);
        return String.format("redirect:/question/detail/%s#answer_%s",answer.getQuestion().getQuestionId(),answer.getAnswerId());
        //this.answerService.create(question,answerDto.getContent(),user);
        //return String.format("redirect:/question/detail/%s",id);
    }

    //답변 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerDto answerDto,@PathVariable("id") Long id, Principal principal) {
        AnswerEntity answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerDto.setContent(answer.getContent());
        return "answer/form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerDto answerDto, BindingResult bindingResult,@PathVariable("id") Long id, Principal principal) {
        if(bindingResult.hasErrors()){
            return "answer/form";
        }
        AnswerEntity answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer,answerDto.getContent());
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getQuestionId(),answer.getAnswerId());
    }

    //답변 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Long id) {
        AnswerEntity answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answerService.delete(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getQuestionId());
    }

    //답변 추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Long id) {
        AnswerEntity answer = this.answerService.getAnswer(id);
        UsersEntity user = this.usersService.getUser(principal.getName());
        this.answerService.vote(answer, user);
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getQuestionId(),answer.getAnswerId());
    }
}
