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
    @GetMapping("/toDeptManage")
    public String DeptManage(){
        return "page/DeptManage";
    }

    @GetMapping("/toEmployeeManage")
    public String employee(){
        return "page/EmployeeManage";
    }

    @GetMapping("/toProfessional")
    public String professional(){
        return  "page/Professional";
    }

    @GetMapping("/toLeave")
    public String leave(){
        return  "page/Leave";
    }

    @GetMapping("/toAttendance")
    public String attendance(){
        return "page/Attendance";
    }

    @GetMapping("/toBussinessTrip")
    public String tbussinessTrip(){
        return "page/BussinessTrip";
    }

    @GetMapping("/toAward")
    public String award(){
        return "page/Award";
    }

    @GetMapping("/toSalary")
    public String salary(){
        return "page/Salary";
    }

    @GetMapping("/toHRManager")
    public String HRManage(){
        return "page/HRManage";
    }

    @GetMapping("/toApply")
    public String apply(){
        return "page/Apply";
    }

    @GetMapping("/toDetail1")
    public String detail1(){
        return "page/NewsDetail1";
    }

    @GetMapping("/toDetail2")
    public String detail2(){
        return "page/NewsDetail2";
    }

    @GetMapping("/toDetail3")
    public String detail3(){
        return "page/NewsDetail3";
    }

}
