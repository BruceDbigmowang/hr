package com.example.hr.dao;

import com.example.hr.pojo.BussinessTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BussinessTripDAO extends JpaRepository<BussinessTrip , Integer> {

    @Query(value = "update BussinessTrip set status=?2 where id=?1" , nativeQuery = true)
    @Modifying
    void update(int id , String status);
    @Query(value = "select  * from  bussinesstrip where account=?1 and day like CONCAT(?2,'%') and status=?3",nativeQuery = true)
    List<BussinessTrip> findByAccountAndDayLikeAndStatus(String account , String day , String status);
}
