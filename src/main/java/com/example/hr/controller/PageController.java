package com.example.hr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

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
    @GetMapping("/forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:toLogin";
    }
    @GetMapping("/toNews")
    public String news(){
        return "page/News";
    }
    @GetMapping("toIntroduce")
    public String introduce(){
        return "page/Introduce";
    }
    @GetMapping("/toRecruitment")
    public String Recruitment(){
        return "page/Recruitment";
    }
    @GetMapping("/toMessage")
    public String Message(){
        return "page/Message";
    }

}
