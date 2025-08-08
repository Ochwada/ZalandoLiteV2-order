package com.owr.order_service.dto.request;


import com.owr.order_service.model.Status;

/*=================================================================================
 * Project: order-service
 * File: UpdateOrderStatusRequest
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 1:03 PM
 * Description: Used in the controller to update Status
 * - Request body for updating an order's status.
 =================================================================================*/
public record UpdateOrderStatusRequest(Status status) { }
