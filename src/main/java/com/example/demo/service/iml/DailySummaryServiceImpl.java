package com.example.demo.service.iml;

import com.example.demo.Dto.DailySummaryDto;

import com.example.demo.Dto.DistrictSummaryDto;
import com.example.demo.Model.DailySummary;
import com.example.demo.Model.District;
import com.example.demo.Model.DistrictSummary;
import com.example.demo.Repository.DailySummaryRepository;
import com.example.demo.Repository.DistrictRepository;
import com.example.demo.Repository.DistrictSummaryRepository;
import com.example.demo.service.DailySummaryService;
import com.example.demo.utility.DateUtil;
import gui.ava.html.image.generator.HtmlImageGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DailySummaryServiceImpl implements DailySummaryService {

    private final ModelMapper mapper;
    private final DailySummaryRepository summaryRepository;
    private final DistrictSummaryRepository districtSummaryRepository;
    private final DistrictRepository districtRepository;


    @Override
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

    @Override
    public void populateDistrictSummary() {
        try {
            String url = "https://corona-api.cramstack.com/api/v1/districts";
            // create headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-api-key", " NMBDNY6-K6T4JC2-KS0AVWK-VX6FBKJ");
            headers.setContentType(MediaType.APPLICATION_JSON);
            // create request
            HttpEntity request = new HttpEntity(headers);
            // make a request
            ResponseEntity<DistrictSummaryDto[]> response = new RestTemplate().exchange(url, HttpMethod.GET, request, DistrictSummaryDto[].class);
            insertDistrict(Arrays.asList(response.getBody()));
            System.out.println(response);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Page<DailySummary> fetchDailySummary(Date startDate, Date endDate, int page, int size) {
        return summaryRepository.findAllByDateBetween(startDate, endDate, PageRequest.of(page,size));
    }

    @Override
    public File fetchDistrictSummary(int bbsCode) throws IOException {
        District district = districtRepository.findByBbsCode(bbsCode);
        if(district == null) throw new EntityNotFoundException("No District Found with BBS Code: "+ bbsCode);
        DistrictSummary summary =  districtSummaryRepository.findByDistrictId(district.getId());
        return generateDistrictSummaryImage(summary, district);
    }

    @Override
    public File fetchSummaryByDate(String input) throws IOException, ParseException {
        Date date;
        if (input.equals(""))  date = new Date();
        else date = DateUtil.toDateWithOutZone(input);
        DailySummary dailySummary = summaryRepository.findByDate(date);
        if(dailySummary == null) return  generateDailySummaryImage(new DailySummary());
        return generateDailySummaryImage(dailySummary);
    }

    private void insertData(List<DailySummaryDto> summaryList){
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

    private void insertDistrict(List<DistrictSummaryDto> summaryList){
        List<DistrictSummary> list = summaryList.stream().map(dto -> {
            DistrictSummary entity = mapper.map(dto, DistrictSummary.class);
            District district = districtRepository.findByNameEnOrNameBn(dto.getNameEng(),dto.getNameBn());
            entity.setDistrictId(district.getId());
            System.out.println("saving: -----> "+ dto.getNameEng());
            districtSummaryRepository.save(entity);
            return entity;
        }).collect(Collectors.toList());

    }

    private File generateDailySummaryImage( DailySummary dailySummary) throws IOException {
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
                "        <td>{2}</td>\n" +
                "        <td>{3}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>Recovery</td>\n" +
                "        <td>{4}</td>\n" +
                "        <td>{5}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>Test for COVID</td>\n" +
                "        <td>{6}</td>\n" +
                "        <td>{7}</td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n",
                dailySummary.getConfirmedCase(), dailySummary.getCumulativeConfirmedCases(),
                dailySummary.getDeath(), dailySummary.getCumulativeDeath(),
                dailySummary.getRecovery(), dailySummary.getCumulativeRecovery(),
                dailySummary.getDailyTestsForCovid19(),dailySummary.getTotalTestsForCovid19());
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        File img = new File("summary.png");
        img.createNewFile();
        imageGenerator.loadHtml(html);
        try {
            imageGenerator.getBufferedImage();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imageGenerator.saveAsImage(img);
        return img;
    }

    private File generateDistrictSummaryImage( DistrictSummary summary, District district) throws IOException {
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
                        "<h2>COVID-19 District Summary</h2>\n" +
                        "\n" +
                        "<table>\n" +
                        "    <tr>\n" +
                        "        <th>{0}</th>\n" +
                        "        <th>{1}</th>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td>Total Case</td>\n" +
                        "        <td>{2}</td>\n" +
                        "\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td>Total Deaths</td>\n" +
                        "        <td>{3}</td>\n" +
                        "\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td>Total Recovery</td>\n" +
                        "        <td>{4}</td>\n" +
                        "    </tr>\n" +
                        "</table>\n" +
                        "</body>\n" +
                        "</html>",
                district.getNameEn(), summary.getLatLon(),
                summary.getCases(), summary.getDeath(), summary.getRecovery());
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        File img = new File("summary.png");
        img.createNewFile();
        imageGenerator.loadHtml(html);
        try {
            imageGenerator.getBufferedImage();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imageGenerator.saveAsImage(img);
        return img;
    }
}
