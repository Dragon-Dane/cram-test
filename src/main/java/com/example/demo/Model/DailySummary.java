package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "daily_summary")
public class DailySummary {

    @Id
    @Column(name = "Date")
    private Date iDate;

    @Column(name = "Confirmed_Cases_in_Last_24_Hour")
    private int confirmedCase;

    @Column(name = "Death_in_Last_24_Hour")
    private int death;

    @Column(name = "Recovery_in_Last_24_Hour")
    private int recovery;

    @Column(name = "Cumulative_Confirmed_Cases")
    private int cumulativeConfirmedCases;

    @Column(name = "Cumulative_Death")
    private int cumulativeDeath;

    @Column(name = "Cumulative_Recovery")
    private int cumulativeRecovery;

    @Column(name = "Daily_Tests_for_COVID19_")
    private int dailyTestsForCovid19;

    @Column(name = "Confirmed_new_cases_per_day")
    private int confirmedNewCasesPerDay;

    @Column(name = "Total_Tests_for_COVID19_")
    private int totalTestsForCovid19;

}
