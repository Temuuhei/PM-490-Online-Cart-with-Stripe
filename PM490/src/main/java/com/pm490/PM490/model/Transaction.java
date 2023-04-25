//authos munkhdalai
package com.pm490.PM490.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private PaymentMethod paymentMethod;
    private String concept;
    private double amount;
    private LocalDate dateShipped;

    public Transaction(PaymentMethod paymentMethod, String concept, double amount, LocalDate dateShipped, User user) {
        this.paymentMethod = paymentMethod;
        this.concept = concept;
        this.amount = amount;
        this.dateShipped = dateShipped;
        this.user = user;
    }
}
