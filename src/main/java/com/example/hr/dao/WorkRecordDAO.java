package com.example.hr.dao;

import com.example.hr.pojo.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface WorkRecordDAO extends JpaRepository<WorkRecord , Integer> {
    List<WorkRecord> findByAccountAndSignDay(String account , String signDay);

    @Query(value = "update WorkRecord set edate = ?2 where id = ?1" , nativeQuery = true)
    @Modifying
    void update(int id , Timestamp edate);

    List<WorkRecord> findBySignDay(String signDay);
}
