package com.example.hr.controller;

import com.example.hr.pojo.Profession;
import com.example.hr.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProfessionController {
    @Autowired
    ProfessionService professionService;

    @RequestMapping("/findAllProfession")
    public List<Profession> getAll(){
        return professionService.findAll();
    }

    @RequestMapping("/createProfession")
    public void create(@RequestParam("pcode")String pcode , @RequestParam("pname")String pname){
        Profession profession = new Profession();
        profession.setPcode(pcode);
        profession.setPname(pname);
        professionService.save(profession);
    }

    @RequestMapping("/professionExist")
    public boolean exist(@RequestParam("pcode")String pcode){
        return professionService.exist(pcode);
    }
}
