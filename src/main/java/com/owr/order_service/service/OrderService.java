package com.owr.order_service.service;


import com.owr.order_service.dto.request.CreateOrderRequest;
import com.owr.order_service.dto.response.OrderResponse;
import com.owr.order_service.model.Order;
import com.owr.order_service.model.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
     * Places a new order based on the provided request details and authentication token.
     * *
     * This method performs the following steps:
     *  - Validates stock availability for each requested item using the inventory service with Bearer token authentication
     *  - Maps the incoming {@link CreateOrderRequest} to an {@link Order} entity
     *  - Persists the order in the repository</li>
     *  - Decreases inventory stock via the inventory service</li>
     *  - Maps the saved order to an {@link OrderResponse}</li>
     * </ul>
     *
     * @param request the {@link CreateOrderRequest} containing the customer's order items
     * @param token   the Bearer token for authenticated requests to the inventory service
     * @return the created {@link OrderResponse} with order ID, created timestamp, items, status, and total price
     * @throws IllegalArgumentException if the available stock is insufficient for any item
     */
    //OrderResponse placeOrder(CreateOrderRequest request);
    OrderResponse placeOrder(CreateOrderRequest request, String token);

    /**
     * Updates the status of an existing order.
     *
     * @param orderId   the ID of the order to update
     * @param newStatus the new status to set
     */
    OrderResponse updateOrderStatus(String orderId, Status newStatus);

    /**
     * Retrieves all orders created within a given date range.
     *
     * @param from optional start date (inclusive)
     * @param to   optional end date (inclusive). If not provided, defaults to same as start date.
     * @return list of {@link OrderResponse} DTOs created within the specified range
     */
    List<OrderResponse> getOrdersByDateRange(LocalDate from, LocalDate to);


}

