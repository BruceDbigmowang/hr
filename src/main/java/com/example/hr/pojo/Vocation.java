package com.example.hr.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "vocation")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class Vocation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "leaveDay")
    String leaveDay;
    @Column(name = "duration")
    int duration;
    @Column(name = "reason")
    String reason;
    @Column(name = "bdate")
    Timestamp bdate;
    @Column(name = "edate")
    Timestamp edate;
    @Column(name = "account")
    String account;
    @Column(name = "status")
    String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeaveDay() {
        return leaveDay;
    }

    public void setLeaveDay(String leaveDay) {
        this.leaveDay = leaveDay;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getBdate() {
        return bdate;
    }

    public void setBdate(Timestamp bdate) {
        this.bdate = bdate;
    }

    public Timestamp getEdate() {
        return edate;
    }

    public void setEdate(Timestamp edate) {
        this.edate = edate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
