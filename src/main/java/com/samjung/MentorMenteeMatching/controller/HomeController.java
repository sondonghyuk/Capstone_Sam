package com.samjung.MentorMenteeMatching.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String mainPage() {
        return "home/mainpage"; // 템플릿 위치를 지정합니다.
    }
}