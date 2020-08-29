package com.example.demo.Repository;


import com.example.demo.Model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    District findByNameEnOrNameBn(String nameEn,String nameBn);

    District findByBbsCode(int bbsCode);
}
