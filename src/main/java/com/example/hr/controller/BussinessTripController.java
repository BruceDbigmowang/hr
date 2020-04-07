package com.example.hr.controller;

import com.example.hr.pojo.BussinessTrip;
import com.example.hr.pojo.Vocation;
import com.example.hr.service.BussinessTripService;
import com.example.hr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@RestController
public class BussinessTripController {
    @Autowired
    BussinessTripService bussinessTripService;
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/getAllBussinessTrip")
    public List<BussinessTrip> getAll(){
        List<BussinessTrip> bussinessTripList = bussinessTripService.findAll();
        for(int i = 0 ; i < bussinessTripList.size() ; i++){
            BussinessTrip bussinessTrip = new BussinessTrip();
            String account = bussinessTrip.getAccount();
            String name = employeeService.getEmployeeName(account);
            if(name != null){
                bussinessTrip.setAccount(name);
            }
        }
        return bussinessTripList;
    }

    @RequestMapping("/changeBussinessTripStatus")
    public void changeStatus(@RequestParam("id")int id){
        bussinessTripService.update(id);
    }

    @RequestMapping("/saveBussinessTrip")
    public String save(@RequestParam("account")String account , @RequestParam("bdate")Timestamp bdate , @RequestParam("edate")Timestamp edate , @RequestParam("reason")String reason){
        if(bdate.after(edate)){
            //判断开始时间早于结束时间
            return "请假开始时间应早于请假结束时间";
        }else{
            System.out.println("执行到此处"+2);
            int year1 = bdate.getYear();
            int year2 = edate.getYear();
            //限制请假时间不可超过1年
            if(year2-year1 > 0){
                return "请假不可超过一年";
            }else{

                System.out.println("执行到此处"+4);
                int month1 = bdate.getMonth();
                int month2 = edate.getMonth();
                //限制请假时间不能超过一个月
                if(month2-month1 > 0){
                    return "请假不可超过一个月";
                }else{
                    System.out.println("执行到此处"+5);
                    int day1 = bdate.getDate();
                    int day2 = edate.getDate();
                    int days = day2-day1;
                    int hour1 = bdate.getHours();
                    int hour2 = edate.getHours();
                    int duration = 0;
                    if(hour1 < 9){
                        return "请假开始时间应晚于上午9点";
                    }
                    if(hour2 > 17){
                        return "请假结束时间不应晚于下午5点";
                    }
                    if(days == 0){
                        duration = hour2-hour1;
                        System.out.println("执行到此处"+2);
                    }else{
                        System.out.println("执行到此处"+1);
                        if(hour2 < 9){
                            return "9点之前的时间不在请假范围之内";
                        }

                        for(int s = day1 ; s <= day2 ; s++){

                            String targetDay = "";
                            String yearMonth = new SimpleDateFormat("yyyy-MM").format(bdate);
                            if(day1 < 10){
                                targetDay = yearMonth+"-"+"0"+s;
                            }else{
                                targetDay = yearMonth+"-"+s;
                            }
                            System.out.println(targetDay);
                            LocalDate nowDate = LocalDate.parse(targetDay);
                            int flag1 = nowDate.getDayOfWeek().getValue();
                            if(flag1!=6 && flag1!=7){
                               if(s == day2){
                                   duration = duration+hour2-9;
                                   System.out.println(duration+"->");
                               }else{

                                   duration = duration+8;
                                   System.out.println(duration+"->>");
                               }
                            }
                        }

                    }
                    System.out.println(duration);
                    String leaveDay =new SimpleDateFormat("yyyy-MM-dd").format(bdate);
                    BussinessTrip bussinessTrip = new BussinessTrip();
                    bussinessTrip.setAccount(account);
                    bussinessTrip.setDay(leaveDay);
                    bussinessTrip.setDuration(duration);
                    bussinessTrip.setReason(reason);
                    bussinessTrip.setBdate(bdate);
                    bussinessTrip.setEdate(edate);
                    //新建请假流程，默认状态为未审批：F
                    bussinessTrip.setStatus("F");
                    bussinessTripService.save(bussinessTrip);
                    return "信息录入成功";
                }
            }

        }
    }
}
