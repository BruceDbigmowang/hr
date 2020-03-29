package com.example.hr.controller;

import com.example.hr.pojo.DeptAndEmployee;
import com.example.hr.pojo.Employee;
import com.example.hr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/getEmployee")
    public List<Employee> findByDept(@RequestParam("dept")String dept){
        List<DeptAndEmployee> deptAndEmployeeList =  employeeService.findByDept(dept);
        List<Employee> employees = new ArrayList<>();
        for(int i = 0 ; i < deptAndEmployeeList.size() ; i++){
            String account = deptAndEmployeeList.get(i).getAccount();
            Employee employee = employeeService.findByAccount(account);
            if(employee != null){
                employees.add(employee);
            }
        }
        return  employees;
    }

    @RequestMapping("checkAccount")
    public boolean exist(@RequestParam("account")String account){
        return employeeService.checkExist(account);
    }

    @RequestMapping("/saveEmployee")
    public  void save(@RequestParam("deptCode")String dept , @RequestParam("account")String account , @RequestParam("name")String name){
        employeeService.saveAll(dept , account , name);
    }
}
