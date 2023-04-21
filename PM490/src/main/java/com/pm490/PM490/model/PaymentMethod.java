package com.pm490.PM490.model;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String type;
    private String fullname;
    private long number;
    private String expireDate;
    private int cvv;
    private int zipcode;
}
