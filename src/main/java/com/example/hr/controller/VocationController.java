package com.example.hr.controller;

import com.example.hr.pojo.Vocation;
import com.example.hr.service.EmployeeService;
import com.example.hr.service.VocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@RestController
public class VocationController {
    @Autowired
    VocationService vocationService;
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/getAllVocation")
    public List<Vocation> finadAllVocation(){
        List<Vocation> vocationList = vocationService.finAll();
        for(int i = 0 ; i < vocationList.size() ; i++){
            Vocation vocation = vocationList.get(i);
            String account = vocation.getAccount();
            String name = employeeService.getEmployeeName(account);
            if(name != null){
                vocation.setAccount(name);
            }
        }
        return vocationList;
    }

    @RequestMapping("/saveVocation")
    public String createVocation(@RequestParam("reason")String reason , @RequestParam("bdate")Timestamp bdate , @RequestParam("edate")Timestamp edate , @RequestParam("account")String account){
        if(bdate.after(edate)){
            //判断开始时间早于结束时间
            return "请假开始时间应早于请假结束时间";
        }else{
            int year1 = bdate.getYear();
            int year2 = edate.getYear();
            //限制请假时间不可超过1年
            if(year2-year1 > 0){
                return "请假不可超过一年";
            }else{
                int month1 = bdate.getMonth();
                int month2 = edate.getMonth();
                //限制请假时间不能超过一个月
                if(month2-month1 > 0){
                    return "请假不可超过一个月";
                }else{
                    int day1 = bdate.getDay();
                    int day2 = edate.getDay();
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

                        String leaveDay =new SimpleDateFormat("yyyy-MM-dd").format(bdate);
                        LocalDate localDate = LocalDate.parse(leaveDay);
                        int value = localDate.getDayOfWeek().getValue();
                        if(value==6 ||value==7){
                            return "今天是周末，不需要请假";
                        }
                        duration = hour2-hour1;
                    }else{
                        //判断起始日期与结束日期是否是周末，若是周末，返回提示消息修改时间
                        String leaveDay =new SimpleDateFormat("yyyy-MM-dd").format(bdate);
                        LocalDate localDate = LocalDate.parse(leaveDay);
                        int value = localDate.getDayOfWeek().getValue();
                        if(value==6 ||value==7){
                            return "起始日期是周末，不需要请假";
                        }
                        String endDate =new SimpleDateFormat("yyyy-MM-dd").format(edate);
                        LocalDate elocalDate = LocalDate.parse(endDate);
                        int value2 = elocalDate.getDayOfWeek().getValue();
                        if(value2==6 ||value2==7){
                            return "结束日期是周末，不需要请假";
                        }
                        int date1 = bdate.getDate();
                        int date2 = edate.getDate();

                        String yearAndMonth = new SimpleDateFormat("yyyy-MM").format(bdate);
                        for(int s = date1 ; s <= date2 ; s++){
                            if(s != date2){
                                if(s <10){
                                    String nowaday1 = yearAndMonth+"-"+"0"+s;
                                    LocalDate nowDate = LocalDate.parse(nowaday1);
                                    int flag1 = nowDate.getDayOfWeek().getValue();
                                    if(flag1!=6 && flag1!=7){
                                        duration = duration+8;
                                    }
                                }else{
                                    String nowaday1 = yearAndMonth+"-"+s;
                                    LocalDate nowDate = LocalDate.parse(nowaday1);
                                    int flag1 = nowDate.getDayOfWeek().getValue();
                                    if(flag1!=6 && flag1!=7){
                                        duration = duration+8;
                                    }
                                }
                            }else{
                                if(s <10){
                                    String nowaday1 = yearAndMonth+"-"+"0"+s;
                                    LocalDate nowDate = LocalDate.parse(nowaday1);
                                    int flag1 = nowDate.getDayOfWeek().getValue();
                                    if(flag1!=6 && flag1!=7){
                                        duration = duration+hour2-9;
                                    }
                                }else{
                                    String nowaday1 = yearAndMonth+"-"+s;
                                    LocalDate nowDate = LocalDate.parse(nowaday1);
                                    int flag1 = nowDate.getDayOfWeek().getValue();
                                    if(flag1!=6 && flag1!=7){
                                        duration = duration+hour2-9;
                                    }
                                }
                            }
                        }
                        if(hour2 < 9){
                            return "9点之前的时间不在请假范围之内";
                        }
                    }
                    String leaveDay =new SimpleDateFormat("yyyy-MM-dd").format(bdate);
                    Vocation vocation = new Vocation();
                    vocation.setAccount(account);
                    vocation.setLeaveDay(leaveDay);
                    vocation.setDuration(duration);
                    vocation.setReason(reason);
                    vocation.setBdate(bdate);
                    vocation.setEdate(edate);
                    //新建请假流程，默认状态为未审批：F
                    vocation.setStatus("F");
                    vocationService.save(vocation);
                    return "信息录入成功";
                }
            }

        }
    }

    @RequestMapping("/changeVocationStatus")
    public void changeStatus(@RequestParam("id")int id){
        String status = "T";
        vocationService.update(id , status);
    }
}
