package com.example.hr.service;

import com.example.hr.dao.BussinessTripDAO;
import com.example.hr.pojo.BussinessTrip;
import com.example.hr.pojo.Vocation;
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

    public List<BussinessTrip> findByMonth(String account , String day){
        List<BussinessTrip> bussinessTripList = bussinessTripDAO.findByAccountAndDayLikeAndStatus(account , day , "T");
        if(bussinessTripList == null && bussinessTripList.size() == 0){
            return null;
        }else{
            return bussinessTripList;
        }
    }
}
