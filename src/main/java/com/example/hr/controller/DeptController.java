package com.example.hr.controller;

import com.example.hr.pojo.Dept;
import com.example.hr.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    DeptService deptService;

    @RequestMapping("/getAllDept")
    public List<Dept> findAll() {
        return deptService.findAll();
    }

    @RequestMapping("/checkDept")
    public String check(@RequestParam("deptCode")String code){
        boolean exist = deptService.exist(code);
        if(exist == true){
            return "此部门代码已重复，更换代码再提交";
        }else{
            return "OK";
        }
    }

    @RequestMapping("/createDept")
    public void save(@RequestParam("deptCode")String deptCode , @RequestParam("deptName")String deptName){
        Dept dept = new Dept();
        dept.setCode(deptCode);
        dept.setName(deptName);
        deptService.save(dept);
    }

 }
