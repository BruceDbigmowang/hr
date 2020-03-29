package com.example.hr.dao;

import com.example.hr.pojo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDAO extends JpaRepository<Employee , Integer> {

    List<Employee> findByAccount(String account);
}
