package com.example.hr.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "message")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "messageContent")
    String content;
    @Column(name = "account")
    String account;
    @Column(name = "name")
    String name;
    @Column(name = "DataTime")
    Date DataTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public Date getDataTime() {
        return DataTime;
    }

    public void setDataTime(Date dataTime) {
        DataTime = dataTime;
    }
}
