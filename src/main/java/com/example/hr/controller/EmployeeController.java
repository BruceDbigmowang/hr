package com.example.hr.controller;

import com.example.hr.pojo.DeptAndEmployee;
import com.example.hr.pojo.Employee;
import com.example.hr.service.EmployeeService;
import com.example.hr.service.ProfessionService;
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
    @Autowired
    ProfessionService professionService;

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
        for(int i = 0 ; i < employees.size() ; i++){
            Employee employee = employees.get(i);
            String pname = professionService.getDescribe(employee.getProfession());
            if(pname != null){
                employee.setProfession(pname);
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

    @RequestMapping("/changeProfession")
    public void updateProfession(@RequestParam("account")String account , @RequestParam("profession")String profession){
        Employee employee = employeeService.findByAccount(account);
        employeeService.updateProfession(employee.getId() , profession);
    }

    @RequestMapping("/changeSalary")
    public void updateSalary(@RequestParam("account")String account , @RequestParam("salary")int salary){
        Employee employee = employeeService.findByAccount(account);
        employeeService.updateSalary(employee.getId() , salary);
    }
}
