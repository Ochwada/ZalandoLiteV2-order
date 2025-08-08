package com.owr.order_service.dto.response;


/*=================================================================================
 * Project: order-service
 * File: OrderLineItemResponse
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 10:51 AM
 * Description:  A single item inside an order response.
 =================================================================================*/


public record OrderLineItemResponse(
        Long productId,
        int quantity
) { }
