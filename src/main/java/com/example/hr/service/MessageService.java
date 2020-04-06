package com.example.hr.service;

import com.example.hr.dao.MessageDAO;
import com.example.hr.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;

    public void save(Message message){
        messageDAO.save(message);
    }
    //insert into Message(messageContent , account , name , Datatime)VALUES(....)

    public List<Message> findAll(){
        return messageDAO.findAll();
        // select * from Message;
    }
}
