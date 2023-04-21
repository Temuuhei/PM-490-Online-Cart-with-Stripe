package com.pm490.PM490.service;

import com.pm490.PM490.model.Transaction;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    String generateReport(LocalDate localDate, String fileFormat) throws JRException, IOException;
    List<Transaction> findAllTransactions();
}
