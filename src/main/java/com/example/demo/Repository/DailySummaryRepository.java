package com.example.demo.Repository;

import com.example.demo.Model.DailySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;

public interface DailySummaryRepository extends JpaRepository<DailySummary, Date> {
}
