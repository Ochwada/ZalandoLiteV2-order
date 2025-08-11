package com.owr.order_service.model;

import com.owr.order_service.dto.request.OrderItemRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
/*=================================================================================
 * Project: order-service
 * File: OrderLineItem
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 9:50 AM
 * Description: Represents a single product item in an order . Contains the product ID and the quantity ordered.
 =================================================================================*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItem {
    /** The unique identifier of the product. */
    private Long productId;

    /** The quantity of the product ordered. */
    private int quantity;

    double price;

}
