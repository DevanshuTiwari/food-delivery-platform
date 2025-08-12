package com.dt.order_service.order.service;

import com.dt.order_service.order.dto.OrderRequest;
import com.dt.order_service.order.dto.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
}
