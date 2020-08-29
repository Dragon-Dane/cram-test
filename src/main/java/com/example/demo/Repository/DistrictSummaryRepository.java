package com.example.demo.Repository;


import com.example.demo.Model.DistrictSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictSummaryRepository extends JpaRepository<DistrictSummary, Long> {


    @Query("select d from district_summary d where d.districtId =:id")
    DistrictSummary findByDistrictId(@Param(("id")) Long id);


}
