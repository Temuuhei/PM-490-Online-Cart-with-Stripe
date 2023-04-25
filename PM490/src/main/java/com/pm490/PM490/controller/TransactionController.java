package com.pm490.PM490.controller;

import com.pm490.PM490.model.Transaction;
import com.pm490.PM490.model.User;
import com.pm490.PM490.payload.request.TransactionRequest;
import com.pm490.PM490.payload.response.MessageResponse;
import com.pm490.PM490.service.CurrentUserService;
import com.pm490.PM490.service.TransactionService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;


@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    CurrentUserService currentUserService;


    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        try {
            User user = currentUserService.findLoggedUser();
            Transaction transaction = transactionService.createTransaction(transactionRequest, user);
            return ResponseEntity.ok(new MessageResponse("Transaction created successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error creating transaction."));
        }
    }

    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return transactionService.exportReport(format);
    }
}
