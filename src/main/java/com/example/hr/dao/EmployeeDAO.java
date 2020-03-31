package com.example.hr.dao;

import com.example.hr.pojo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeDAO extends JpaRepository<Employee , Integer> {

    List<Employee> findByAccount(String account);


    @Modifying
    @Query(value = "update Employee set profession = ?2 where id = ?1", nativeQuery = true)
    void updateProfession(int id , String profession);

    @Transactional
    @Modifying
    @Query(value = "update Employee set salary = ?2 where id = ?1" , nativeQuery = true)
    void updateSalary(int id , int salary);
}
