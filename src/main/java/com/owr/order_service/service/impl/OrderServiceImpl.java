package com.owr.order_service.service.impl;


import com.owr.order_service.dto.request.CreateOrderRequest;
import com.owr.order_service.dto.response.OrderResponse;
import com.owr.order_service.repository.OrderRepository;
import com.owr.order_service.service.OrderService;
import com.owr.order_service.service.client.InventoryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/*=================================================================================
 * Project: order-service
 * File: OrderServiceImpl
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 11:36 AM
 * Description: Service implementation containing the business logic for placing orders.
 =================================================================================*/
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private InventoryClient client;

    @Override
    public List<OrderResponse> getAllOrders() {
        return List.of();
    }

    @Override
    public OrderResponse placeOrder(CreateOrderRequest request) {
        return null;
    }
}
