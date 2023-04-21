package com.pm490.PM490.dto;

import com.pm490.PM490.model.ProductStatus;
import lombok.Data;
@Data
public class ProductRequest {

    private String name;
    private String color;
    private ProductStatus status;
    private int quantity;
    private long idCategory;
    private double price;

}
