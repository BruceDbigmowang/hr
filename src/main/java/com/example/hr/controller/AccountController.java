package com.example.hr.controller;

import com.example.hr.pojo.Account;
import com.example.hr.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @RequestMapping("/doRegister")
    public int register(@RequestParam("account")String account , @RequestParam("name")String name , @RequestParam("password")String password){
        int i = accountService.has(account);
        if(i == 0){
            return 0;
        }else {

            accountService.register(account , name , password);
            return 1;
        }
    }

    @RequestMapping("/doLogin")
    public int login(@RequestParam("account")String account , @RequestParam("password")String password , HttpSession session){
        Account user = accountService.login(account , password);
        if(null == user){
            return 0;
        }else{
            if(user.getRole().equals("user")){
                session.setAttribute("user",user);
                return 1;
            }else{
                session.setAttribute("user",user);
                return 2;
            }
        }
    }
}
