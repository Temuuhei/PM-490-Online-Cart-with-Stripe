package com.pm490.PM490.dto;

import com.pm490.PM490.model.PurchaseStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderRequest {
    private LocalDate dateOrdered;
    private LocalDate dateShipped;
    private PurchaseStatus status;
}
