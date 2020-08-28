package com.example.demo.service.iml;

import com.example.demo.Dto.DailySummaryDto;

import com.example.demo.Model.DailySummary;
import com.example.demo.Repository.DailySummaryRepository;
import com.example.demo.service.DailySummaryService;
import com.example.demo.utility.DateUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DailySummaryServiceIml implements DailySummaryService {

    private final ModelMapper mapper;
    private final DailySummaryRepository summaryRepository;


    public void populateDailySummary() {
        try {
            String url = "https://corona-api.cramstack.com/api/v1/daily-summery";
            // create headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-api-key", " NMBDNY6-K6T4JC2-KS0AVWK-VX6FBKJ");
            headers.setContentType(MediaType.APPLICATION_JSON);
            // create request
            HttpEntity request = new HttpEntity(headers);
            // make a request
            ResponseEntity<DailySummaryDto[]> response = new RestTemplate().exchange(url, HttpMethod.GET, request, DailySummaryDto[].class);
            insertData(Arrays.asList(response.getBody()));
            System.out.println(response);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }


    public Page<DailySummary> fetchDailySummary(Date startDate, Date endDate, int page, int size) {
        return summaryRepository.findAllByDateBetween(startDate, endDate, PageRequest.of(page,size));
    }

    public void insertData(List<DailySummaryDto> summaryList){
        List<DailySummary> list = summaryList.stream().map(dto -> {
            DailySummary entity = mapper.map(dto, DailySummary.class);
            try {
                entity.setIDate(DateUtil.toDateWithOutZone(dto.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return entity;
        }).collect(Collectors.toList());
        summaryRepository.saveAll(list);
    }

}
