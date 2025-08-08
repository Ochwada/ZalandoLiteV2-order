package com.owr.order_service.service;


import com.owr.order_service.dto.request.CreateOrderRequest;
import com.owr.order_service.dto.response.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/*=================================================================================
 * Project: order-service
 * File: OrderService
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 11:27 AM
 * Description: Service layer for order operations. Service class for handling business logic related to orders
 =================================================================================*/
@Service
public interface OrderService {
    /**
     * Retrieves a list of all orders in the system.
     *
     * @return a list of {@link OrderResponse} DTOs representing all stored orders
     */
    List<OrderResponse> getAllOrders();

    /**
     * Places a new order based on the provided request details.
     * *
     * - This method typically checks inventory availability, calculates total price, and persists the order.
     *
     * @param request the {@link CreateOrderRequest} containing the customer's order details
     * @return the created {@link OrderResponse} with order ID, items, and timestamp
     */
    OrderResponse placeOrder(CreateOrderRequest request);
}

