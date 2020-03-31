package com.example.hr.dao;

import com.example.hr.pojo.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AwardDAO extends JpaRepository<Award , Integer> {
    @Transactional
    @Query(value = "update Award set status = ?2 where id = ?1" , nativeQuery = true)
    @Modifying
    void updateStatus(int id , String status);
}
