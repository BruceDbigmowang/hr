package com.example.hr.dao;

import com.example.hr.pojo.DeptAndEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeptAndEmployeeDAO extends JpaRepository<DeptAndEmployee , Integer> {
    List<DeptAndEmployee> findByDept(String dept);

    void deleteByAccount(String account);
}
