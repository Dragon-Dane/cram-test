package com.example.demo.RestController;

import com.example.demo.Model.DailySummary;
import com.example.demo.Model.DistrictSummary;
import com.example.demo.service.DailySummaryService;
import com.example.demo.utility.DateUtil;
import gui.ava.html.image.generator.HtmlImageGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.activation.FileTypeMap;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CramstackController  {

    private final DailySummaryService summaryService;

    @GetMapping("summary/image")
    public ResponseEntity<byte[]> summaryImage(@RequestParam(value = "date", defaultValue = "",required = false) String date) throws IOException, ParseException {
        File img =  summaryService.fetchSummaryByDate(date);
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
    }

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

    @GetMapping("summary/district/{bbsCode}")
    public ResponseEntity<byte[]> fetchSummary(@PathVariable("bbsCode")int bbsCode ) throws IOException {
        File img =  summaryService.fetchDistrictSummary(bbsCode);
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));

    }


}
