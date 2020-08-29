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
import java.text.ParseException;
import java.util.Date;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CramstackController  {

    private final DailySummaryService summaryService;

    @GetMapping("test")
    public ResponseEntity<byte[]> test() throws IOException {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        File img = new File("hello.png");
        img.createNewFile();
         imageGenerator.loadHtml("<!DOCTYPE html>\n" +
                 "<html>\n" +
                 "<head>\n" +
                 "    <meta charset=\"UTF-8\">\n" +
                 "    <title>Title</title>\n" +
                 "</head>\n" +
                 "<body>\n" +
                 "\n" +
                 "\n" +
                 "<h1 style=\"background-color:Blue;\">Tomato</h1>\n" +
                 "<h1 style=\"background-color:Blue;\">Orange</h1>\n" +
                 "<h1 style=\"background-color:Blue;\">DodgerBlue</h1>\n" +
                 "<h1 style=\"background-color:Blue;\">MediumSeaGreen</h1>\n" +
                 "<h1 style=\"background-color:Blue;\">Gray</h1>\n" +
                 "<h1 style=\"background-color:Blue;\">SlateBlue</h1>\n" +
                 "<h1 style=\"background-color:Blue;\">Violet</h1>\n" +
                 "<h1 style=\"background-color:Blue;\">LightGray</h1>\n" +
                 "</body>\n" +
                 "</html>");
         imageGenerator.setSize(new Dimension(6000,6000));
        try {
          imageGenerator.getBufferedImage();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         imageGenerator.saveAsImage(img);
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
    public DistrictSummary fetchSummary(@PathVariable("bbsCode")int bbsCode ) {
        return summaryService.fetchDistrictSummary(bbsCode);
    }


}
