package com.example.hr.dao;

import com.example.hr.pojo.DeptAndEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeptAndEmployeeDAO extends JpaRepository<DeptAndEmployee , Integer> {
    List<DeptAndEmployee> findByDept(String dept);
}
