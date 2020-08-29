package com.example.demo.scheduling;

import com.example.demo.service.DailySummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduledTasks {

    private final DailySummaryService summaryService;

    @Scheduled(fixedRate = 28800000)
    public void populate() {
        summaryService.populateDailySummary();
    }

    @Scheduled(fixedRate = 28800000)
    public void districtDatePopulate() {
        summaryService.populateDistrictSummary();
    }
}
