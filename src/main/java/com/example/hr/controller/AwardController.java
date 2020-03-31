package com.example.hr.controller;

import com.example.hr.pojo.Award;
import com.example.hr.service.AwardService;
import com.example.hr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class AwardController {
    @Autowired
    AwardService awardService;
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("getAllAwardRecord")
    public List<Award> allAwards(){
        List<Award> awardList = awardService.findAll();
        for(int i = 0 ; i < awardList.size() ; i++){
            Award award = awardList.get(i);
            String name = employeeService.getEmployeeName(award.getAccount());
            if(name != null){
                award.setAccount(name);
            }
        }
        return awardList;
    }

    /**
     * 创建一条奖励记录
     * 时间为当前时间
     * 奖励发放状态 未发放：O
     * 后期奖励发放之后，修改状态 已发放：C
     * @param account
     * @param reason
     * @param way
     */
    @RequestMapping("/writeAward")
    public void saveAward(@RequestParam("account")String account , @RequestParam("reason")String reason , @RequestParam("way")String way){
        Award award = new Award();
        award.setAccount(account);
        award.setReason(reason);
        award.setWay(way);
        award.setDatetime(new Date());
        award.setStatus("O");//未完成奖励发放
        awardService.save(award);
    }

    @RequestMapping("changeStatus")
    public void updateStatus(@RequestParam("id")int id){
        awardService.update(id , "C");
    }

}
