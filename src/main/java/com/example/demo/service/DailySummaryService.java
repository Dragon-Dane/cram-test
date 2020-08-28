package com.example.demo.service;

import com.example.demo.Model.DailySummary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public interface DailySummaryService {
    void populateDailySummary();
    void populateDistrictSummary();
    Page<DailySummary> fetchDailySummary(Date sDate, Date eDate, int page, int size);

}
