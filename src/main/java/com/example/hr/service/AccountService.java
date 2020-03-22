package com.example.hr.service;

import com.example.hr.dao.AccountDAO;
import com.example.hr.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountDAO accountDAO;

    public Account login(String account , String password){
        return accountDAO.findByAccountAndPassword(account , password);
    }

    public void register(String account , String name , String password){
        Account user = new Account();
        user.setAccount(account);
        user.setName(name);
        user.setPassword(password);
        user.setRole("user");
        accountDAO.save(user);
    }
    public int has(String account){
        System.out.println(account);
        List<Account> users = accountDAO.findByAccount(account);
        System.out.println(users.size());
        if(null == users || users.size() ==0){
            System.out.println("______");
            return 1;
        }else{
            System.out.println("=======");
            return 0;
        }
    }
}
