package com.pm490.PM490.service;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class CronService {

    @Autowired
    ReportService reportService;

    /**
     * TODO: CHANGE FIXED RATE TO MONTLY
     * @Scheduled(fixedDelayString = "${interval}")
     * @Scheduled(cron = "@hourly")  //once an hour, (0 0 * * * *)
     * @Scheduled(cron = "@daily")  //once a day (0 0 0 * * *) @midnight also does the same thing.
     * @Scheduled(cron = "@weekly")  //once a week (0 0 0 * * 0)
     * @Scheduled(cron = "@monthly")  //once a month (0 0 0 1 * *)
     * @Scheduled(cron = "@yearly")
    */
    @Scheduled(fixedRateString = "${spring.cron.duration}")
    public void computePrice() throws JRException, IOException {
        String fileLink = reportService.generateReport(LocalDate.now(), "pdf");
        System.out.println("Scheduled job is running !!! "+LocalDate.now());
    }
}
