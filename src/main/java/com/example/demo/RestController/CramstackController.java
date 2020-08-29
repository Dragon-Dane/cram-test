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
    public ResponseEntity<byte[]> summaryImage(@RequestParam) throws IOException {


        String html = MessageFormat.format("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        table '{'\n" +
                "            font-family: arial, sans-serif;\n" +
                "            border-collapse: collapse;\n" +
                "            width: 100%;\n" +
                "        '}'\n" +
                "\n" +
                "        td, th '{'\n" +
                "            border: 1px solid #dddddd;\n" +
                "            text-align: left;\n" +
                "            padding: 8px;\n" +
                "        '}'\n" +
                "\n" +
                "        tr:nth-child(even) '{'\n" +
                "            background-color: #dddddd;\n" +
                "       ' }'\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h2>COVID-19 Summary</h2>\n" +
                "\n" +
                "<table>\n" +
                "    <tr>\n" +
                "        <th>#</th>\n" +
                "        <th>Last 24 Hour</th>\n" +
                "        <th>Total</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>New Case</td>\n" +
                "        <td>{0}</td>\n" +
                "        <td>{1}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>Death Case</td>\n" +
                "        <td>{0}</td>\n" +
                "        <td>{1}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>Recovery</td>\n" +
                "        <td>{0}</td>\n" +
                "        <td>{1}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>Test for COVID</td>\n" +
                "        <td>{0}</td>\n" +
                "        <td>{1}</td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n", 25, "30" );
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        File img = new File("hello.png");
        img.createNewFile();
         imageGenerator.loadHtml(html);
        try {
          imageGenerator.getBufferedImage();
            Thread.sleep(1000);
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
