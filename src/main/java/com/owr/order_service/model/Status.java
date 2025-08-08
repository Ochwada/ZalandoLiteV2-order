package com.owr.order_service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
/*=================================================================================
 * Project: order-service
 * File: Status
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 9:52 AM
 * Description: Represents the various statuses an order can have during its lifecycle.
 =================================================================================*/


/**
 * This enum is used to track and control the progress of an order, from initial placement to final delivery or
 * cancellation.
 * This enum is typically stored as a field in the {@code Order} entity.
 */
@RequiredArgsConstructor
public enum Status {
    // Order has been placed but not yet processed
    PENDING("Pending"),

    // Order is being prepared or packed
    PROCESSING("Processing"),

    // Order has been dispatched to the customer
    SHIPPED("Shipped"),

    // Order has been delivered successfully
    DELIVERED("Delivered"),

    // Order has been cancelled before shipping
    CANCELLED("Cancelled"),

    // Order was delivered but returned by the customer
    RETURNED("Returned");

    private final String value;

    /**
     * Serializes the enum to its title-case string value (e.g, "Pending")
     */
    @JsonValue
    @Override
    public String toString() {
        return value;
    }


    /**
     * Deserializes a case-insensitive string into a matching {@code Status} enum constant.
     * *
     * This method is used by Jackson when parsing JSON input (e.g., from API or MongoDB).
     * It allows values like "pending", "PENDING", or "Pending" to correctly map to {@code Status.PENDING}.
     *
     * @param value the string representation of the status (case-insensitive)
     * @return the matching {@code Status} enum constant
     * @throws IllegalArgumentException if the input does not match any known status
     */
    @JsonCreator
    public static Status fromValue(String value) {
        // Iterate through all enum values
        for (Status status : values()) {
            // Match input string with each status value (case-insensitive)
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        // If no match is found, throw an exception
        throw new IllegalArgumentException("Unknown status: " + value);
    }

}
