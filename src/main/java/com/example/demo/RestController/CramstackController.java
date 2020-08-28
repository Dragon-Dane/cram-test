package com.example.demo.RestController;

import com.example.demo.Dto.DailySummaryDto;
import com.example.demo.service.DailySummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CramstackController  {

    private final DailySummaryService summaryService;

    @GetMapping("summary")
    public void fetchSummary() {
        summaryService.fetchAndPopulateDailySummary();
    }

}
