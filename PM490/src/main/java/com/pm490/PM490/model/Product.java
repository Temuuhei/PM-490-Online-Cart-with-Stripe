package com.pm490.PM490.model;

import lombok.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String color;
    @ManyToOne
    private User vendor;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private int quantity;
    @ManyToOne
    private Category category;
    private double price;
    @OneToMany
    private List<ItemList> itemList;
    private String description;



    public Product(String name, String color, User vendor, ProductStatus status, int quantity, Category category, double price) {
        this.name = name;
        this.color = color;
        this.vendor = vendor;
        this.status = status;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
    }
}
