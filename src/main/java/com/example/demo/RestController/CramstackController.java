package com.example.demo.RestController;

import com.example.demo.Model.DailySummary;
import com.example.demo.service.DailySummaryService;
import com.example.demo.utility.DateUtil;
import gui.ava.html.image.generator.HtmlImageGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.FileTypeMap;
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
         imageGenerator.loadHtml("<!DOCTYPE html>\n" +
                 "<html lang=\"en\">\n" +
                 "<head>\n" +
                 "    <meta charset=\"UTF-8\">\n" +
                 "    <title>Title</title>\n" +
                 "</head>\n" +
                 "<body>\n" +
                 "<!DOCTYPE html>\n" +
                 "<html>\n" +
                 "<body>\n" +
                 "\n" +
                 "<h1 style=\"background-color:Tomato;\">Tomato</h1>\n" +
                 "<h1 style=\"background-color:Orange;\">Orange</h1>\n" +
                 "<h1 style=\"background-color:DodgerBlue;\">DodgerBlue</h1>\n" +
                 "<h1 style=\"background-color:MediumSeaGreen;\">MediumSeaGreen</h1>\n" +
                 "<h1 style=\"background-color:Gray;\">Gray</h1>\n" +
                 "<h1 style=\"background-color:SlateBlue;\">SlateBlue</h1>\n" +
                 "<h1 style=\"background-color:Violet;\">Violet</h1>\n" +
                 "<h1 style=\"background-color:LightGray;\">LightGray</h1>\n" +
                 "\n" +
                 "</body>\n" +
                 "</html>\n" +
                 "</body>\n" +
                 "</html>");
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
}
