package com.owr.order_service.dto.response;


/*=================================================================================
 * Project: order-service
 * File: InventoryResponse
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 4:00 PM
 * Description: 
 =================================================================================*/
public record InventoryResponse(
        Long productId,
        int quantity,
        double price
) {
}
