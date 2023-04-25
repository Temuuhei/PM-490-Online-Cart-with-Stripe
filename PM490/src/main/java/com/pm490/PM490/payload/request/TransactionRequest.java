package com.pm490.PM490.payload.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionRequest {
    private long paymentMethodId;
    private String concept;
    private double amount;
    private LocalDate dateShipped;
    private long idUser;
}
