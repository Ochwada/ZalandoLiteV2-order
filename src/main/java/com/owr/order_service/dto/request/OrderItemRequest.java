package com.owr.order_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
/*=================================================================================
 * Project: order-service
 * File: OrderItemRequest
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 10:47 AM
 * Description: A record representing a single item in an order request.
 =================================================================================*/

/**
 * Represents a single product entry in an order request.
 * *
 * This record is used within {@link CreateOrderRequest} to define the desired quantity and price for a specific product.
 *
 * @param productId the ID of the product to order; must not be {@code null}
 * @param quantity  the quantity of the product to order; must be at least 1
 * @param price     the price of a single unit of the product at the time of order
 */
public record OrderItemRequest(
        @NotNull Long productId,
        @Min(1) int quantity,
        @DecimalMin(value = "0.00", inclusive = false) double price
) { }
