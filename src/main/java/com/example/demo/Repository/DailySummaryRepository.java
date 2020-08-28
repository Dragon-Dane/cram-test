package com.example.demo.Repository;

import com.example.demo.Model.DailySummary;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface DailySummaryRepository extends JpaRepository<DailySummary, Date> {

    @Query("select s from daily_summary s where s.iDate between :sDate  and :eDate")
    Page<DailySummary> findAllByDateBetween(@Param("sDate") Date startDate, @Param("eDate") Date endDate, Pageable pageable);
}
