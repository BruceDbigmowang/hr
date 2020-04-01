package com.example.hr.service;

import com.example.hr.dao.BussinessTripDAO;
import com.example.hr.pojo.BussinessTrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BussinessTripService {
    @Autowired
    BussinessTripDAO bussinessTripDAO;

    public List<BussinessTrip> findAll(){
        return bussinessTripDAO.findAll();
    }

    public void save(BussinessTrip bussinessTrip){
        bussinessTripDAO.save(bussinessTrip);
    }

    @Transactional
    public void update(int id){
        bussinessTripDAO.update(id , "T");
    }
}
