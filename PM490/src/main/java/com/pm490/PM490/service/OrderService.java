package com.pm490.PM490.service;

import com.pm490.PM490.dto.OrderRequest;
import com.pm490.PM490.dto.OrderSearchDto;
import com.pm490.PM490.model.OrderCart;

import java.util.List;

public interface OrderService {
    List<OrderCart> showAll();
    List<OrderCart> searchOrder(Long searchOrder);
    OrderCart save(OrderRequest order) throws RuntimeException;
    OrderCart update(long id, OrderRequest order);
}
