package com.pm490.PM490.service;


import com.pm490.PM490.model.Role;
import com.pm490.PM490.model.Transaction;
import com.pm490.PM490.model.User;
import com.pm490.PM490.payload.request.TransactionRequest;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface TransactionService {

    Double getTotalRecAmountByUserRole(String userRole);


    public String exportReport(String reportFormat) throws FileNotFoundException, JRException;

    Transaction createTransaction(TransactionRequest transactionRequest, User user);
}
