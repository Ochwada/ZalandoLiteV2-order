package com.owr.order_service.dto.response;

import java.time.LocalDateTime;
/*=================================================================================
 * Project: order-service
 * File: OrderResponse
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 10:50 AM
 * Description: Response DTO sent back when an order is placed.
 =================================================================================*/

public record OrderResponse(
        String id,
        LocalDateTime createdAt,
        List<OrderLineItemResponse> items
) {
}
