package com.example.hr.dao;

import com.example.hr.pojo.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeptDAO extends JpaRepository<Dept , Integer> {


    List<Dept> findByCode(String code);
}
