package com.example.hr.service;

import com.example.hr.dao.ProfessionDAO;
import com.example.hr.pojo.Profession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionService {
    @Autowired
    ProfessionDAO professionDAO;

    public List<Profession> findAll(){
        return professionDAO.findAll();
    }

    public void save(Profession profession){
        professionDAO.save(profession);
    }

    public boolean exist(String pcode){
        List<Profession> professionList = professionDAO.findByPcode(pcode);
        if(professionList == null || professionList.size() == 0){
            return false;
        }else{
            return true;
        }
    }

    public String getDescribe(String pcode){
        List<Profession> professionList = professionDAO.findByPcode(pcode);
        if(professionList == null || professionList.size() == 0){
            return null;
        }else{
            return professionList.get(0).getPname();
        }
    }
}
