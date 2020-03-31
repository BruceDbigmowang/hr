package com.example.hr.service;

import com.example.hr.dao.DeptAndEmployeeDAO;
import com.example.hr.dao.EmployeeDAO;
import com.example.hr.pojo.DeptAndEmployee;
import com.example.hr.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    DeptAndEmployeeDAO deptAndEmployeeDAO;

    @Autowired
    EmployeeDAO employeeDAO;

    public List<DeptAndEmployee> findByDept(String dept){
        return deptAndEmployeeDAO.findByDept(dept);
    }

    public Employee findByAccount(String account){
        List<Employee> employees = employeeDAO.findByAccount(account);
        if(employees != null && employees.size() != 0){
            return employees.get(0);
        }else{
            return null;
        }
    }

    public boolean checkExist(String account){
        List<Employee> employees = employeeDAO.findByAccount(account);
        if(employees != null && employees.size() != 0){
            return false;
        }else{
            return true;
        }
    }

    public void saveAll(String deptCode , String account , String name){
        DeptAndEmployee deptAndEmployee = new DeptAndEmployee();
        deptAndEmployee.setDept(deptCode);
        deptAndEmployee.setAccount(account);
        deptAndEmployeeDAO.save(deptAndEmployee);

        Employee employee = new Employee();
        employee.setAccount(account);
        employee.setName(name);
        employeeDAO.save(employee);
    }

    @Transactional
    public void updateProfession(int id , String profession){
        employeeDAO.updateProfession(id , profession);
    }

    public void updateSalary(int id , int salary){
        employeeDAO.updateSalary(id , salary);
    }

    public String getEmployeeName(String account){
        List<Employee> employeeList = employeeDAO.findByAccount(account);
        if(employeeList == null || employeeList.size() == 0){
            return null;
        }else{
            return employeeList.get(0).getName();
        }
    }
}
