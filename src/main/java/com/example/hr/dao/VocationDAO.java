package com.example.hr.dao;

import com.example.hr.pojo.Vocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VocationDAO extends JpaRepository<Vocation , Integer> {
    @Query(value = "update vocation set status=?2 where id=?1" , nativeQuery = true)
    @Modifying
    void update(int id , String status);

    List<Vocation> findByAccountAndLeaveDayAndStatus(String account , String leaveDay , String status);
    @Query(value = "select  * from  Vocation where account=?1 and leaveDay like CONCAT(?2,'%') and status=?3",nativeQuery = true)
    List<Vocation> findByAccountAndLeaveDayLikeAndStatus(String account , String leaveDay , String status);

}
