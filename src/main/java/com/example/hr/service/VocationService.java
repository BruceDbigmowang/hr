package com.example.hr.service;

import com.example.hr.dao.VocationDAO;
import com.example.hr.pojo.Vocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VocationService {
    @Autowired
    VocationDAO vocationDAO;

    public List<Vocation> finAll(){
        return vocationDAO.findAll();
    }

    public void save(Vocation vocation){
        vocationDAO.save(vocation);
    }

    @Transactional
    public void update(int id , String status){
        vocationDAO.update(id , status);
    }
}
