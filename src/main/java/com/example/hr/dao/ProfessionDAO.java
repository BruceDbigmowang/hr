package com.example.hr.dao;

import com.example.hr.pojo.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessionDAO extends JpaRepository<Profession , Integer> {

    List<Profession> findByPcode(String pcode);
}
