package com.example.hr.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Controller;

import javax.persistence.*;

@Entity
@Table(name = "Account")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class Account {
    @Id
    @Column(name = "account")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String account;

    @Column(name = "name")
    String name;
    @Column(name = "password")
    String password;
    @Column(name = "role")
    String role;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
