package com.pm490.PM490.dto;

import com.pm490.PM490.model.Role;
import com.pm490.PM490.model.User;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
public class PaymentMethodRequest {
    private Long userId;
    private Role role;
    private String type;
    private String fullname;
    private long number;
    private String expireDate;
    private int cvv;
    private int zipcode;
}
