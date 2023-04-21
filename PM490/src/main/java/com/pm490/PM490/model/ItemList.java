package com.pm490.PM490.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ItemList {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Product product;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;
    private double total;
}
