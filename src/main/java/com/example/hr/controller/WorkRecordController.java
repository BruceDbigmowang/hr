package com.example.hr.controller;

import com.example.hr.pojo.BussinessTrip;
import com.example.hr.pojo.Vocation;
import com.example.hr.pojo.WorkRecord;
import com.example.hr.service.BussinessTripService;
import com.example.hr.service.VocationService;
import com.example.hr.service.WorkRecordService;
import com.example.hr.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
public class WorkRecordController {
    @Autowired
    WorkRecordService workRecordService;
    @Autowired
    VocationService vocationService;
    @Autowired
    BussinessTripService bussinessTripService;

    @RequestMapping("/getThisMonthRecord")
    public List<Result> getThisMonth(@RequestParam("account")String account){
        List<Result> resultList = new ArrayList<>();
        /**
         * 1、获取当前日期
         * 2、从当前月的1号开始检查，检查每天的打卡时间
         * 若上班卡时间在9点之前，下班卡时间在17点之后，考勤正常
         * 若上班时间在9点之后，一小时之内按一小时计算，迟到xx小时
         * 若下班时间在17点之前，不足一小时按一小时计算，早退xx小时
         *
         * 若当天没有数据
         * 查询请假表和出差表中的数据
         * 若两表中均无数据，则考勤异常:当天没有考勤数据
         * 若有数据，检查时间
         */

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前日期
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH)+1;
        int day = ca.get(Calendar.DATE);
        if(day == 1){
            return resultList;
        }else{
            for(int i = 1 ; i < day ; i++){
                if(i<10){
                    String checkDate = "";
                    if(month < 10){
                        checkDate = year+"-"+"0"+month+"-"+"0"+i;
                    }else{
                        checkDate = year+"-"+month+"-"+"0"+i;
                    }
                    //若检查日期为周末，直接跳过
                    //正常工作日，则正常检查
                    LocalDate localDate = LocalDate.parse(checkDate);
                    int flag1 = localDate.getDayOfWeek().getValue();
                    if(flag1!=6 && flag1!=7){
                        //检查工作日的

                        WorkRecord workRecord = workRecordService.findByAccountAndSignDay(account , checkDate);
                        if(workRecord == null){
                            //若没有记录，检查请假 出差表，是否有记录，有记录时间是否正确
                            int target = 0;
                            //检查请假
                            String yearAndMonth = "";
                            if(month < 10){
                                yearAndMonth = year+"-"+"0"+month;
                            }else{
                                yearAndMonth = year+"-"+month;
                            }
                            List<Vocation> vocationList = vocationService.getByMonth(account , yearAndMonth);
                            if(vocationList != null && vocationList.size()!=0){

                                for (int j = 0 ; j < vocationList.size() ; j++){
                                    Vocation vocation = vocationList.get(j);


//                                    Timestamp morning = Timestamp.valueOf(checkDate+" 09:00:00");
//                                    Timestamp night = Timestamp.valueOf(checkDate+" 17:00:00");
//
//                                    System.out.println("<<<<<<<<<");
//                                    System.out.println(morning.before(vocation.getBdate()));
//                                    System.out.println(night.after(vocation.getEdate()));
//                                    System.out.println("<<<<<<<<<");


                                    int duration = vocation.getDuration();
                                    if(duration < 8 && vocation.getLeaveDay().equals(checkDate)){
                                        Result consequence = new Result();
                                        consequence.setAccount(account);
                                        consequence.setSignDay(checkDate);
                                        consequence.setResult(checkDate+":考勤异常：未打卡，请假时间小于工作时间");
                                        resultList.add(consequence);
                                        target += 1;
                                        break;
                                    }else if(duration == 8 && vocation.getLeaveDay().equals(checkDate)){
                                        Result consequence = new Result();
                                        consequence.setAccount(account);
                                        consequence.setSignDay(checkDate);
                                        consequence.setResult(checkDate+":考勤正常：已请假");
                                        resultList.add(consequence);
                                        target += 1;
                                        break;
                                    }else {
                                        Timestamp morning = Timestamp.valueOf(checkDate+" 09:00:00");
                                        Timestamp night = Timestamp.valueOf(checkDate+" 17:00:00");

                                        System.out.println("<<<<<<<<<");
                                        System.out.println(morning.before(vocation.getBdate()));
                                        System.out.println(night.after(vocation.getEdate()));
                                        System.out.println("<<<<<<<<<");

                                        if(!morning.before(vocation.getBdate()) && !night.after(vocation.getEdate())){
                                            Result consequence = new Result();
                                            consequence.setAccount(account);
                                            consequence.setSignDay(checkDate);
                                            consequence.setResult(checkDate+":考勤正常：已请假");
                                            resultList.add(consequence);
                                            target += 1;
                                            break;
                                        }
                                    }
                                }

                            }else{
                                List<BussinessTrip> bussinessTripList = bussinessTripService.findByMonth(account , yearAndMonth);
                                for(int j = 0 ; j < bussinessTripList.size() ; j++){

                                    BussinessTrip bussinessTrip = bussinessTripList.get(j);
                                    if(bussinessTrip.getDay().equals(checkDate) && bussinessTrip.getDuration() < 8){
                                        Result consequence = new Result();
                                        consequence.setAccount(account);
                                        consequence.setSignDay(checkDate);
                                        consequence.setResult(checkDate+":考勤异常：未打卡，出差时间少于工作时间");
                                        resultList.add(consequence);
                                        target += 1;
                                        break;
                                    }else if(bussinessTrip.getDay().equals(checkDate) && bussinessTrip.getDuration() == 8){
                                        Result consequence = new Result();
                                        consequence.setAccount(account);
                                        consequence.setSignDay(checkDate);
                                        consequence.setResult(checkDate+":考勤正常：已出差");
                                        resultList.add(consequence);
                                        target += 1;
                                        break;
                                    }else{
                                        Timestamp morning = Timestamp.valueOf(checkDate+" 09:00:00");
                                        Timestamp night = Timestamp.valueOf(checkDate+" 17:00:00");
                                        if(!morning.before(bussinessTrip.getBdate()) && !night.after(bussinessTrip.getEdate())){
                                            Result consequence = new Result();
                                            consequence.setAccount(account);
                                            consequence.setSignDay(checkDate);
                                            consequence.setResult(checkDate+":考勤正常：已出差");
                                            resultList.add(consequence);
                                            target += 1;
                                            break;
                                        }
                                    }

                                }



                            }
                            if(target == 0){
                                Result consequence = new Result();
                                consequence.setAccount(account);
                                consequence.setSignDay(checkDate);
                                consequence.setResult(checkDate+":考勤异常：未打卡");
                                resultList.add(consequence);
                            }
                        }else{
                            //若有记录，检查打卡时间是否正确，若打卡时间符合要求 正常记录
                            //若有记录，时间不符合要求，检查请假表中的数据
                            Timestamp bdate = workRecord.getBdate();
                            Timestamp edate = workRecord.getEdate();
                            if(bdate == null || edate == null){
                                Vocation vocation = vocationService.getByAccountAndLeaveDay(account , checkDate);
                                if(vocation == null){
                                    Result consequence = new Result();
                                    consequence.setAccount(account);
                                    consequence.setSignDay(checkDate);
                                    consequence.setResult(checkDate+":考勤异常：上班或下班未打卡");
                                    resultList.add(consequence);
                                    continue;
                                }else{
                                    int duration = vocation.getDuration();
                                    if( duration == 8){
                                        Result consequence = new Result();
                                        consequence.setAccount(account);
                                        consequence.setSignDay(checkDate);
                                        consequence.setResult(checkDate+":考勤正常");
                                        resultList.add(consequence);
                                        continue;
                                    }else{
                                        Result consequence = new Result();
                                        consequence.setAccount(account);
                                        consequence.setSignDay(checkDate);
                                        consequence.setResult(checkDate+":考勤异常：上班或下班未打卡且请假时间小于8小时");
                                        resultList.add(consequence);
                                        continue;
                                    }
                                }
                            }

                        }


                    }
                }else{

                    String checkDate = year+"-"+month+"-"+i;
                    //若检查日期为周末，直接跳过
                    //正常工作日，则正常检查
                    LocalDate localDate = LocalDate.parse(checkDate);
                    int flag1 = localDate.getDayOfWeek().getValue();
                    if(flag1!=6 && flag1!=7){
                        //检查工作日的

                        WorkRecord workRecord = workRecordService.findByAccountAndSignDay(account , checkDate);
                        if(workRecord == null){
                            //若没有记录，检查请假 出差表，是否有记录，有记录时间是否正确
                            int target = 0;
                            //检查请假
                            String yearAndMonth = year+"-"+month;
                            System.out.println("-----"+yearAndMonth);
                            List<Vocation> vocationList = vocationService.getByMonth(account , yearAndMonth);
                            if(vocationList != null && vocationList.size()!=0){

                                for (int j = 0 ; j < vocationList.size() ; j++){
                                    Vocation vocation = vocationList.get(j);
                                    int duration = vocation.getDuration();
                                    if(duration < 8 && vocation.getLeaveDay().equals(checkDate)){
                                        Result consequence = new Result();
                                        consequence.setAccount(account);
                                        consequence.setSignDay(checkDate);
                                        consequence.setResult(checkDate+":考勤异常：未打卡，请假时间小于工作时间");
                                        resultList.add(consequence);
                                        target += 1;
                                        break;
                                    }else if(duration == 8 && vocation.getLeaveDay().equals(checkDate)){
                                        Result consequence = new Result();
                                        consequence.setAccount(account);
                                        consequence.setSignDay(checkDate);
                                        consequence.setResult(checkDate+":考勤正常：已请假");
                                        resultList.add(consequence);
                                        target += 1;
                                        break;
                                    }else {
                                        Timestamp morning = Timestamp.valueOf(checkDate+" 09:00:00");
                                        Timestamp night = Timestamp.valueOf(checkDate+" 17:00:00");

                                        System.out.println("<<<<<<<<<");
                                        System.out.println(morning.before(vocation.getBdate()));
                                        System.out.println(night.after(vocation.getEdate()));
                                        System.out.println("<<<<<<<<<");
                                        if(!morning.before(vocation.getBdate()) && !night.after(vocation.getEdate())){
                                            Result consequence = new Result();
                                            consequence.setAccount(account);
                                            consequence.setSignDay(checkDate);
                                            consequence.setResult(checkDate+":考勤正常：已请假");
                                            resultList.add(consequence);
                                            target += 1;
                                            break;
                                        }
                                    }
                                }

                            }else{
                                List<BussinessTrip> bussinessTripList = bussinessTripService.findByMonth(account , yearAndMonth);
                                for(int j = 0 ; j < bussinessTripList.size() ; j++){

                                    BussinessTrip bussinessTrip = bussinessTripList.get(j);
                                    if(bussinessTrip.getDay().equals(checkDate) && bussinessTrip.getDuration() < 8){
                                        Result consequence = new Result();
                                        consequence.setAccount(account);
                                        consequence.setSignDay(checkDate);
                                        consequence.setResult(checkDate+":考勤异常：未打卡，出差时间少于工作时间");
                                        resultList.add(consequence);
                                        target += 1;
                                        break;
                                    }else if(bussinessTrip.getDay().equals(checkDate) && bussinessTrip.getDuration() == 8){
                                        Result consequence = new Result();
                                        consequence.setAccount(account);
                                        consequence.setSignDay(checkDate);
                                        consequence.setResult(checkDate+":考勤正常：已出差");
                                        resultList.add(consequence);
                                        target += 1;
                                        break;
                                    }else{
                                        Timestamp morning = Timestamp.valueOf(checkDate+" 09:00:00");
                                        Timestamp night = Timestamp.valueOf(checkDate+" 17:00:00");
                                        if(!morning.before(bussinessTrip.getBdate()) && !night.after(bussinessTrip.getEdate())){
                                            Result consequence = new Result();
                                            consequence.setAccount(account);
                                            consequence.setSignDay(checkDate);
                                            consequence.setResult(checkDate+":考勤正常：已出差");
                                            resultList.add(consequence);
                                            target += 1;
                                            break;
                                        }
                                    }

                                }



                            }
                            if(target == 0){
                                Result consequence = new Result();
                                consequence.setAccount(account);
                                consequence.setSignDay(checkDate);
                                consequence.setResult(checkDate+":考勤异常：未打卡");
                                resultList.add(consequence);
                            }
                        }else{
                            //若有记录，检查打卡时间是否正确，若打卡时间符合要求 正常记录
                            //若有记录，时间不符合要求，检查请假表中的数据
                            Timestamp bdate = workRecord.getBdate();
                            Timestamp edate = workRecord.getEdate();
                            if(bdate == null || edate == null){
                                Vocation vocation = vocationService.getByAccountAndLeaveDay(account , checkDate);
                                if(vocation == null){
                                    Result consequence = new Result();
                                    consequence.setAccount(account);
                                    consequence.setSignDay(checkDate);
                                    consequence.setResult(checkDate+":考勤异常：上班或下班未打卡");
                                    resultList.add(consequence);
                                    continue;
                                }else{
                                    int duration = vocation.getDuration();
                                    if( duration == 8){
                                        Result consequence = new Result();
                                        consequence.setAccount(account);
                                        consequence.setSignDay(checkDate);
                                        consequence.setResult(checkDate+":考勤正常");
                                        resultList.add(consequence);
                                        continue;
                                    }else{
                                        Result consequence = new Result();
                                        consequence.setAccount(account);
                                        consequence.setSignDay(checkDate);
                                        consequence.setResult(checkDate+":考勤异常：上班或下班未打卡且请假时间小于8小时");
                                        resultList.add(consequence);
                                        continue;
                                    }
                                }
                            }

                        }


                    }
                }
            }
            return resultList;
        }

    }
}
