package com.example.demo.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class DailySummaryDto {

    @JsonProperty("Date")
    private String date;

    @JsonProperty("Confirmed_Cases_in_Last_24_Hour")
    private int confirmedCase;

    @JsonProperty("Death_in_Last_24_Hour")
    private int death;

    @JsonProperty("Recovery_in_Last_24_Hour")
    private int recovery;

    @JsonProperty( "Cumulative_Confirmed_Cases")
    private int cumulativeConfirmedCases;

    @JsonProperty( "Cumulative_Death")
    private int cumulativeDeath;

    @JsonProperty( "Cumulative_Recovery")
    private int cumulativeRecovery;

    @JsonProperty( "Daily_Tests_for_COVID19_")
    private int dailyTestsForCovid19;

    @JsonProperty( "Confirmed_new_cases_per_day")
    private int confirmedNewCasesPerDay;

    @JsonProperty( "Total_Tests_for_COVID19_")
    private int totalTestsForCovid19;



}
