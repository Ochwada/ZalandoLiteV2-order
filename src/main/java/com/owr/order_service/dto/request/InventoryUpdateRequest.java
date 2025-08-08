package com.owr.order_service.dto.request;


/*=================================================================================
 * Project: order-service
 * File: InventoryUpdateRequest
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 12:35 PM
 * Description: Used for Inventory Update -  without the price
 =================================================================================*/
public record InventoryUpdateRequest(
        Long productId,
        int newQuantity
) { }
