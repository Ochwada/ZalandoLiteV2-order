package com.owr.order_service.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;
/*=================================================================================
 * Project: order-service
 * File: CreateOrderRequest
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 10:49 AM
 * Description: Request DTO to receive order creation input.
 * - Includes a list of product items.
 =================================================================================*/




public record CreateOrderRequest(
        /**
         * List of items in the order request.
         * Must not be null.
         */
        @NotNull List<OrderItemRequest> items
) { }
