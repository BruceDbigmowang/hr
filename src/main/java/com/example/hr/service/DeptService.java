package com.example.hr.service;

import com.example.hr.dao.DeptDAO;
import com.example.hr.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {
    @Autowired
    DeptDAO deptDAO;

    public List<Dept> findAll(){
        return deptDAO.findAll();
    }

    public void save(Dept dept){
        deptDAO.save(dept);
    }

    public boolean exist(String code){
        List<Dept> deptList = deptDAO.findByCode(code);
        if(deptList == null || deptList.size() == 0){
            return false;
        }else{
            return true;
        }
    }
}
