package com.samjung.MentorMenteeMatching.controller;

import com.samjung.MentorMenteeMatching.dto.UsersDto;
import com.samjung.MentorMenteeMatching.service.UsersService;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/home")
public class UserController {
    private final UsersService usersService;

    //회원가입
    @GetMapping("/signup")
    public String signup(UsersDto usersDto){
        return "home/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UsersDto usersDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "home/signup";
        }
        if (!usersDto.getPassword().equals(usersDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "home/signup";
        }

        try {
            usersService.create(usersDto.getUsername(),usersDto.getEmail(),usersDto.getPassword(),usersDto.getPhoneNumber(),usersDto.getDepartment(),usersDto.getStudentId(),usersDto.getUserType());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "home/signup";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "home/signup";
        }

        return "redirect:/home/login";
    }

    //로그인(PostMapping 방식은 스프링 시큐리티가 대신 처리함)
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user",new UsersDto());
        return "home/login";
    }
}
