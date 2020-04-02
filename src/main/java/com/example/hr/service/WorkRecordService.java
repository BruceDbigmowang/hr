package com.example.hr.service;

import com.example.hr.dao.WorkRecordDAO;
import com.example.hr.pojo.WorkRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
