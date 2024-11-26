package com.samjung.MentorMenteeMatching.controller;

import com.samjung.MentorMenteeMatching.dto.AnswerDto;
import com.samjung.MentorMenteeMatching.dto.QuestionDto;
import com.samjung.MentorMenteeMatching.entity.QuestionEntity;
import com.samjung.MentorMenteeMatching.entity.UsersEntity;
import com.samjung.MentorMenteeMatching.repository.QuestionRepository;
import com.samjung.MentorMenteeMatching.service.QuestionService;
import com.samjung.MentorMenteeMatching.service.UsersService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Path;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final UsersService usersService;

    @GetMapping("/")
    public String home(){
        return "question/matching";
    }
    @GetMapping("/list")
    public String list(Model model,@RequestParam(value = "page",defaultValue = "0") int page,@RequestParam(value = "kw",defaultValue = "") String kw){
        Page<QuestionEntity> paging = this.questionService.getList(page,kw);
        model.addAttribute("paging",paging);
        model.addAttribute("kw",kw);
        return "question/list";
    }


    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id, AnswerDto answerDto){
        QuestionEntity question = this.questionService.getQuestion(id);
        model.addAttribute("question",question);
        return "question/detail";
    }

    //질문 생성
    @PreAuthorize("isAuthenticated()") //해당 메서드는 로그인한 사용자만 호출
    @GetMapping("/create")
    public String questionCreate(QuestionDto questionDto){
        return "question/form";
    }

    @PreAuthorize("isAuthenticated()")//해당 메서드는 로그인한 사용자만 호출
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionDto questionDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question/form";
        }
        UsersEntity user = this.usersService.getUser(principal.getName());
        this.questionService.create(questionDto.getTitle(), questionDto.getContent(),user);
        return "redirect:/question/list";
    }

    //질문 수정
    //DTO 데이터를 검증하고 로그인한 사용자와 수정하려는 질문의 작성자가 동일한지도 검증
    //검증이 통과되면 modify 메서드 호출해 데이터 수정

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionDto questionDto, @PathVariable Long id,Principal principal){
        QuestionEntity question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        questionDto.setTitle(question.getTitle());
        questionDto.setContent(question.getContent());
        return "question/form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionDto questionDto,BindingResult bindingResult , @PathVariable Long id,Principal principal){
        if(bindingResult.hasErrors()){
            return "question/form";
        }
        QuestionEntity question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        this.questionService.modify(question,questionDto.getTitle(),questionDto.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    //질문 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Long id) {
        QuestionEntity question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/question/list";
    }

    //추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Long id) {
        QuestionEntity question = this.questionService.getQuestion(id);
        UsersEntity user = this.usersService.getUser(principal.getName());
        this.questionService.vote(question, user);
        return String.format("redirect:/question/detail/%s", id);
    }

    // 질문 목록 페이지

    @GetMapping("/matching")
    public String listQuestion(Model model,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "kw", defaultValue = "") String kw) {
        // 제목에 검색어가 포함된 질문만 반환
        Page<QuestionEntity> paging = this.questionService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);  // 검색어를 템플릿에 전달
        return "question/matching";   // matching.html로 렌더링
    }
}
