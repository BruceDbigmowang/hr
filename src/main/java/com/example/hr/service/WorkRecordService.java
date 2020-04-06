package com.example.hr.service;

import com.example.hr.dao.WorkRecordDAO;
import com.example.hr.pojo.WorkRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class WorkRecordService {
    @Autowired
    WorkRecordDAO workRecordDAO;

    public WorkRecord findByAccountAndSignDay(String account , String signDay){
        List<WorkRecord> workRecordList = workRecordDAO.findByAccountAndSignDay(account , signDay);
        if(workRecordList == null || workRecordList.size() == 0){
            return null;
        }else{
            return workRecordList.get(0);
        }
    }

    public void save(WorkRecord workRecord){
        workRecordDAO.save(workRecord);
    }

    @Transactional
    public void update(int id , Timestamp edate){
        workRecordDAO.update(id, edate);
    }

    public boolean exist(String signDay){
        List<WorkRecord> workRecordList = workRecordDAO.findBySignDay(signDay);
        if(workRecordList == null || workRecordList.size()==0){
            return false;
        }else{
            return true;
        }
    }

    public  WorkRecord findBySignDay(String signDay){
        List<WorkRecord> workRecordList = workRecordDAO.findBySignDay(signDay);
        if(workRecordList == null || workRecordList.size()==0){
            return null;
        }else{
            return workRecordList.get(0);
        }
    }
}
