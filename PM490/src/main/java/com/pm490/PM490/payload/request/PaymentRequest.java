package com.pm490.PM490.payload.request;

import com.pm490.PM490.model.OrderCart;
import com.pm490.PM490.model.PaymentMethod;
import lombok.Data;

@Data
public class PaymentRequest {
    private PaymentMethod paymentMethod;
    private OrderCart orderCart;
}
