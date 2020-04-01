package com.example.hr.dao;

import com.example.hr.pojo.BussinessTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BussinessTripDAO extends JpaRepository<BussinessTrip , Integer> {

    @Query(value = "update BussinessTrip set status=?2 where id=?1" , nativeQuery = true)
    @Modifying
    void update(int id , String status);
}
