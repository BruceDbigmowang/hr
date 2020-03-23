package com.example.hr.dao;

import com.example.hr.pojo.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDAO extends JpaRepository<Message , Integer> {
}
