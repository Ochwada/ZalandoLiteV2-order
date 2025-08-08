package com.owr.order_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
/*=================================================================================
 * Project: order-service
 * File: OrderItemRequest
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 10:47 AM
 * Description: A record representing a single item in an order request.
 =================================================================================*/


public record OrderItemRequest(
        /**
         * The ID of the product to order.
         * Must not be null.
         */
        @NotNull Long productId,

        /**
         * Quantity of the product to order.
         * Must be at least 1.
         */
        @Min(1) int quantity
) { }
