package com.pm490.PM490.dto;

import lombok.Data;

@Data
public class ReportDto {
    private long id;
    private String color;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String status;
    private long vendor_id;
    private String vendorName;
    private String orderDate;
    private Double subTotal;
    private Double tax;
    private Double taxedPrice;
    private Double taxedSubTotal;
}
