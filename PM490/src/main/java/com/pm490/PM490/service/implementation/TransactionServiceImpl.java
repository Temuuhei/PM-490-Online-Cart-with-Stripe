
package com.pm490.PM490.service.implementation;

import com.pm490.PM490.model.*;
import com.pm490.PM490.payload.request.TransactionRequest;
import com.pm490.PM490.repository.PaymentMethodRepository;
import com.pm490.PM490.repository.TransactionRepository;
import com.pm490.PM490.repository.UserRepository;
import com.pm490.PM490.service.TransactionService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Override
    public Double getTotalRecAmountByUserRole(String userRole) {
        Double total = transactionRepository.getTotalRecAmountByUserRole(userRole);
        System.out.println("------------------------------"+total+"------------");
        return total;
    }

    @Override
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "/Users/temuujintsogt/Downloads/Report/";
        List<Transaction> transactionReport= transactionRepository.findAll();
        List<TransactionReport> finalReports = new ArrayList<TransactionReport>();

        for (Transaction obj : transactionReport) {
            TransactionReport summary = new TransactionReport( obj.getId(), obj.getConcept(),obj.getAmount(),obj.getDateShipped().toString());
            finalReports.add(summary);
        }
        System.out.println(finalReports);
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:transaction.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(finalReports);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Binod Kathayat");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "transactionReport.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "transactionReport.pdf");
        }
        return path;
    }

    @Override
    public Transaction createTransaction(TransactionRequest transactionRequest, User user) {
        Optional<User> user1 = userRepository.findByUsername(user.getUsername());
        PaymentMethod paymentMethod = paymentMethodRepository.getById(transactionRequest.getPaymentMethodId());
        Transaction transaction = new Transaction(paymentMethod, transactionRequest.getConcept(), transactionRequest.getAmount(), transactionRequest.getDateShipped(),user1.get());
        return transactionRepository.save(transaction);


    }

}
