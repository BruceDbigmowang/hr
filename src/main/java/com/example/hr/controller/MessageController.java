package com.example.hr.controller;

import com.example.hr.pojo.Account;
import com.example.hr.pojo.Message;
import com.example.hr.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
public class MessageController {
    @Autowired
    MessageService messageService;

    @RequestMapping("/editMessage")
    public void save(@RequestParam("message")String message , HttpSession session){
        Account user = (Account)session.getAttribute("user");
        String account = user.getAccount();
        String name = user.getName();
        Message bean = new Message();
        bean.setContent(message);
        bean.setAccount(account);
        bean.setName(name);
        bean.setDataTime(new Date());
        messageService.save(bean);
    }
    @RequestMapping("/getAllMessage")
    public List<Message> allMessage(){
        return messageService.findAll();
    }
}
