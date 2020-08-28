package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "district_summary")
public class DistrictSummary {

    @Id
    private Long districtId;

    @Column(name = "cases")
    private int cases;

    @JsonProperty("lat_lon")
    private String latLon;

    @Column(name = "total_death")
    private int death;

    @Column(name = "total_recover")
    private int recovery;


}
