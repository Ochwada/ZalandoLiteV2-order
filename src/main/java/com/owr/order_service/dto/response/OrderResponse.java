package com.owr.order_service.dto.response;

import com.owr.order_service.model.Status;

import java.time.LocalDateTime;
import java.util.List;
/*=================================================================================
 * Project: order-service
 * File: OrderResponse
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 10:50 AM
 * Description: Response DTO sent back to the client after an order has been successfully placed.
 =================================================================================*/

/**
 * This record contains essential information about an order that has been successfully created or retrieved.
 *
 * It is commonly returned by REST API endpoints such as {@code POST /api/orders} or {@code GET /api/orders/{id}}.
 *
 * @param id         the unique identifier of the order
 * @param createdAt  the timestamp indicating when the order was created
 * @param customerId the ID of the customer who placed the order
 * @param items      the list of items included in the order, each represented as {@link OrderLineItemResponse}
 * @param status     the current {@link Status} of the order (e.g., Pending, Shipped, Delivered)
 * @param totalPrice the total price of all items in the order
 */
public record OrderResponse(
        String id,
        LocalDateTime createdAt,
        String customerId,
        List<OrderLineItemResponse> items,
        Status status,
        double totalPrice
) { }
