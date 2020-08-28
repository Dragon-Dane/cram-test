package com.example.demo.RestController;

import com.example.demo.Model.DailySummary;
import com.example.demo.service.DailySummaryService;
import com.example.demo.utility.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CramstackController  {

    private final DailySummaryService summaryService;

    @GetMapping("summary/populate")
    public void populate() {
        summaryService.populateDailySummary();
    }

    @GetMapping("district/populate")
    public void districtDatePopulate() {
        summaryService.populateDistrictSummary();
    }

    @GetMapping("summary/fetch")
    public Page<DailySummary> fetchSummary(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size, @RequestParam(value = "fromDate", required = false) String fdate, @RequestParam(value = "toDate", required = false) String tdate) throws ParseException {
        Date endDate = new Date();
        Date startDate= new Date();
        if(fdate != null) startDate = DateUtil.toDateWithOutZone(fdate);
        if(tdate != null) endDate = DateUtil.toDateWithOutZone(tdate);
        return summaryService.fetchDailySummary(startDate,endDate,page, size);
    }
}
