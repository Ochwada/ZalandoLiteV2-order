package com.owr.order_service.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

/*=================================================================================
 * Project: order-service
 * File: Order
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 9:49 AM
 * Description: Represents an order placed by a customer.
 * - Order document to be stored in MongoDB
 * -  Includes timestamp and list of ordered items.
 =================================================================================*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    /**
     * Unique identifier for the order.
     */
    @Id
    private String id;

    /**
     * Unique identifier for the customer who placed the order.
     */
    private String customer_id;

    /**
     * List of individual items included in the order.
     */
    private List<OrderLineItem> items;

    /**
     * Total price of all order items combined.
     */
    private double totalPrice;

    /**
     * Current status of the order (e.g., Pending, Shipped).
     * - Stored and displayed in title-case via {@link Status} enum.
     */
    private Status status;

    /**
     * Timestamp indicating when the order was last modified or created.
     * *
     *  - Automatically updated by Spring Data via {@code @LastModifiedDate}.
     *  - Formatted as "yyyy-MM-dd HH:mm:ss" (server's local time zone).
     *  - Example: {@code "2025-08-07 11:50:00"}
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime createdAt;




}
