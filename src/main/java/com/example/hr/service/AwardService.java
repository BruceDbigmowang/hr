package com.example.hr.service;

import com.example.hr.dao.AwardDAO;
import com.example.hr.pojo.Award;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AwardService {
    @Autowired
    AwardDAO awardDAO;

    public void save(Award award){
        awardDAO.save(award);
    }

    public void update(int id , String status){
        awardDAO.updateStatus(id , status);
    }

    public List<Award> findAll(){
        return awardDAO.findAll();
    }
}
