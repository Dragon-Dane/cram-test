package com.example.demo.service;

import com.example.demo.Model.DailySummary;
import com.example.demo.Model.DistrictSummary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@Service
public interface DailySummaryService {
    void populateDailySummary();
    void populateDistrictSummary();
    Page<DailySummary> fetchDailySummary(Date sDate, Date eDate, int page, int size);

    DistrictSummary fetchDistrictSummary(int bbsCode);

    File fetchSummaryByDate(String date) throws IOException, ParseException;
}
