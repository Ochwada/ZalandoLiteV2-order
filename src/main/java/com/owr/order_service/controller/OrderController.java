package com.owr.order_service.controller;


import com.owr.order_service.dto.request.CreateOrderRequest;
import com.owr.order_service.dto.response.OrderResponse;
import com.owr.order_service.model.Status;
import com.owr.order_service.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/*=================================================================================
 * Project: order-service
 * File: OrderController
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 2:55 PM
 * Description:  Exposes endpoints to create and retrieve orders.
 =================================================================================*/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderServiceImpl service;

    /**
     * Retrieves all orders or filters them by optional date range.
     *
     * @param dateFrom optional start date (format: yyyy-MM-dd)
     * @param dateTo   optional end date (format: yyyy-MM-dd)
     * @return list of {@link OrderResponse} representing matching orders
     * *
     * /GET /api/orders
     * /GET /api/orders?date=2025-08-07
     * GET /api/orders?dateFrom=2025-08-07&dateTo=2025-08-08
     */
    @GetMapping
    public List<OrderResponse> getAllOrders(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
    ) {
        if (dateFrom != null || dateTo != null) {
            return service.getOrdersByDateRange(dateFrom, dateTo);
        }
        return service.getAllOrders();
    }

    /**
     * Creates a new order and returns the saved order details.
     *
     * POST /api/orders
     * @param request the {@link CreateOrderRequest} containing customer and item details
     * @return the newly created {@link OrderResponse}
     * @throws IllegalArgumentException if any item exceeds available stock
     */
    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request){
        return service.placeOrder(request);
    }


    /**
     * Request body for updating the status of an existing order.
     * {
     *   "status": "SHIPPED"
     * }
     * This record is used in the {@code PATCH /api/orders/{id}/status} endpoint.
     *
     * @param status the new {@link Status} enum value to set (e.g., {@code SHIPPED}, {@code CANCELLED})
     */
    public record UpdateStatusRequest(Status status) {}


    /**
     * Updates the status of a specific order and returns the updated order.
     * - This endpoint allows clients to update the {@link Status} of an order.
     * - Common values include {@code PENDING}, {@code SHIPPED}, {@code DELIVERED}, etc.
     * - > PATCH /api/orders/abc123/status
     * - On success, returns the updated {@link OrderResponse} with the new status.
     *
     * @param id   the unique ID of the order to be updated (path variable)
     * @param body the request body containing the new {@link Status}
     * @return {@link ResponseEntity} containing the updated {@link OrderResponse}
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable String id,
            @RequestBody UpdateStatusRequest body
    ){
        OrderResponse updated = service.updateOrderStatus(id, body.status());
        return ResponseEntity.ok(updated);
    }
}
