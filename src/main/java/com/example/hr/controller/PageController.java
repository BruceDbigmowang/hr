package com.example.hr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String toFirst(){
        return "redirect:toLogin";
    }
    @GetMapping("/toLogin")
    public String tologin(){
        return "page/login";
    }
    @GetMapping("/toRegister")
    public String register(){
        return "page/register";
    }
    @GetMapping("/toUserIndex")
    public String userIndex(){
        return "page/UserIndex";
    }
    @GetMapping("/toAdminIndex")
    public String adminIndex(){
        return "page/AdminIndex";
    }

}
