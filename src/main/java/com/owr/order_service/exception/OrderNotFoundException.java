package com.owr.order_service.exception;


/*=================================================================================
 * Project: order-service
 * File: OrderNotFoundException
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 12:59 PM
 * Description: Incase the order isnt found.
 =================================================================================*/
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
