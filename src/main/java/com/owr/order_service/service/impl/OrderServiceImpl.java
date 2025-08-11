package com.owr.order_service.service.impl;


import com.owr.order_service.dto.request.CreateOrderRequest;
import com.owr.order_service.dto.request.OrderItemRequest;
import com.owr.order_service.dto.response.OrderLineItemResponse;
import com.owr.order_service.dto.response.OrderResponse;
import com.owr.order_service.exception.OrderNotFoundException;
import com.owr.order_service.mapper.OrderMapper;
import com.owr.order_service.model.Order;
import com.owr.order_service.model.OrderLineItem;
import com.owr.order_service.model.Status;
import com.owr.order_service.repository.OrderRepository;
import com.owr.order_service.service.OrderService;
import com.owr.order_service.service.client.InventoryClient;
import com.owr.order_service.service.client.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private final InventoryClient client;
    private final OrderMapper mapper;
    private final ProductClient productClient;


    /**
     * Retrieves all saved orders from the database and maps them to response DTOs.
     *
     * @return list of all orders
     */
    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = repository.findAll();
        return orders.stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Validates stock availability and places a new order.
     * *
     * This method performs the following steps:
     *  - Validates available stock for each requested product
     *  - Converts the incoming {@link CreateOrderRequest} into an {@link Order} entity
     *  - Saves the order in the database
     *  - Reduces stock in the inventory service for each item
     *  - Maps the saved entity to a {@link OrderResponse} DTO
     *
     * <p>MongoDB handles the generation of the order ID and timestamp automatically.</p>
     *
     * @param request the order creation request from the client
     * @return the created order as a response DTO
     * @throws IllegalArgumentException if any requested item exceeds available stock
     */
    @Override
    public OrderResponse placeOrder(CreateOrderRequest request, String token) {

        if (request.items() == null || request.items().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        // 1. Validate available stock using authenticated request
        for (var item : request.items()) {
            int available = client.getStockQuantity(item.productId(), token);
            if (item.quantity() > available) {
                throw new IllegalArgumentException(
                        "Insufficient stock for ProductId: " + item.productId()
                );
            }
        }

        // 2) Fetch unit prices (product/catalog service)
        Map<Long, Double> priceByProductId = request.items().stream()
                .map(OrderItemRequest::productId)                  // Stream<Long>
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),                           // key: Long productId
                        pid -> productClient.getUnitPrice(pid, token)  // val: Double price
                ));
        // 3) Map to entity WITH prices, then save
        Order order = mapper.toEntity(request, priceByProductId);
        Order savedOrder = repository.save(order);


        // 4) Decrease stock
        for (var item : request.items()) {
            client.decreaseStock(item.productId(), item.quantity(), token);
        }


        return mapper.toResponse(savedOrder);
    }

    /**
     * Updates the status of an existing order.
     *
     * @param orderId   the ID of the order to update
     * @param newStatus the new {@link Status} to set (e.g., SHIPPED, DELIVERED)
     * @throws OrderNotFoundException if no order exists with the given ID
     */
    @Override
    public OrderResponse  updateOrderStatus(String orderId, Status newStatus) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.setStatus(newStatus);

        Order updatedOrder = repository.save(order);
        return mapper.toResponse(updatedOrder);
    }

    @Override
    public List<OrderResponse> getOrdersByDateRange(LocalDate dateFrom, LocalDate dateTo) {

        LocalDate from = dateFrom != null ? dateFrom : LocalDate.now();
        LocalDate to = dateTo != null ? dateTo : from;

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = from.atTime(23,59,59);

        List<Order> orders = repository.findByCreatedAtBetween(start, end);
        return orders.stream()
                .map(mapper:: toResponse)
                .toList();
    }


}
