package com.example.demo.Repository;


import com.example.demo.Model.DistrictSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictSummaryRepository extends JpaRepository<DistrictSummary, Long> {


}
