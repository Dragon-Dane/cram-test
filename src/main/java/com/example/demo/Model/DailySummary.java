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
    private int confirmedCase =0;

    @Column(name = "Death_in_Last_24_Hour")
    private int death = 0;

    @Column(name = "Recovery_in_Last_24_Hour")
    private int recovery=0;

    @Column(name = "Cumulative_Confirmed_Cases")
    private int cumulativeConfirmedCases=0;

    @Column(name = "Cumulative_Death")
    private int cumulativeDeath=0;

    @Column(name = "Cumulative_Recovery")
    private int cumulativeRecovery=0;

    @Column(name = "Daily_Tests_for_COVID19_")
    private int dailyTestsForCovid19 =0;

    @Column(name = "Confirmed_new_cases_per_day")
    private int confirmedNewCasesPerDay=0;

    @Column(name = "Total_Tests_for_COVID19_")
    private int totalTestsForCovid19=0;

}
