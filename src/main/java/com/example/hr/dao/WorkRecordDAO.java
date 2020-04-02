package com.example.hr.dao;

import com.example.hr.pojo.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRecordDAO extends JpaRepository<WorkRecord , Integer> {
    List<WorkRecord> findByAccountAndSignDay(String account , String signDay);
}
