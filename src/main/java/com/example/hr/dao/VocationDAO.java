package com.example.hr.dao;

import com.example.hr.pojo.Vocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VocationDAO extends JpaRepository<Vocation , Integer> {
    @Query(value = "update vocation set status=?2 where id=?1" , nativeQuery = true)
    @Modifying
    void update(int id , String status);

}
