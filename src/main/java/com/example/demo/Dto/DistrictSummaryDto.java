package com.example.demo.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class DistrictSummaryDto {

    @JsonProperty("district_city_eng")
    private String nameEng;

    @JsonProperty("district_city")
    private String nameBn;

    @JsonProperty("lat_lon")
    private String latLon;


    @JsonProperty( "cases")
    private int cases;

    @JsonProperty( "total_death")
    private int death;

    @JsonProperty( "total_recover")
    private int recover;




}
