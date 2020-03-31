package com.example.hr.dao;

import com.example.hr.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountDAO extends JpaRepository<Account, Integer> {

    Account findByAccountAndPassword(String account , String password);

    List<Account> findByAccount(String account);

}
