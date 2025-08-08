package com.owr.order_service.mapper;

import com.owr.order_service.dto.request.CreateOrderRequest;
import com.owr.order_service.dto.request.OrderItemRequest;
import com.owr.order_service.dto.response.OrderLineItemResponse;
import com.owr.order_service.dto.response.OrderResponse;
import com.owr.order_service.model.Order;
import com.owr.order_service.model.OrderLineItem;
import com.owr.order_service.service.client.InventoryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/*=================================================================================
 * Project: order-service
 * File: OrderMapper
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 11:50 AM
 * Description: Manual mapper for converting between order DTOs and entities.
 *  - {@code OrderMapper}  is responsible for converting between Order-related DTOs and entity models.
 =================================================================================*/

/**
 * This includes mapping:
 * - {@link CreateOrderRequest} ➝ {@link Order}
 * - {@link Order} ➝ {@link OrderResponse}
 * Used by {@code OrderServiceImpl} to translate client input into persistent models, and vice versa.
 */
@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final InventoryClient client;

    //=================== Helper Functions ===============================

    /**
     * 1.
     * Maps a list of {@link OrderItemRequest} DTOs to a list of {@link OrderLineItem} entities.
     *
     * @param requestItems the list of items from the incoming order request
     * @return a list of {@link OrderLineItem} ready to be saved with the order
     */
    private List<OrderLineItem> mapToOrderLineItems(List<OrderItemRequest> requestItems) {
        return requestItems.stream()
                .map(item -> new OrderLineItem(
                        item.productId(),
                        item.quantity()
                ))
                .toList();
    }

    /**
     * 2.
     * Maps a list of {@link OrderLineItem} entities to a list of {@link OrderLineItemResponse} DTOs.
     *
     * @param items the list of line items in the order
     * @return a list of {@link OrderLineItemResponse} containing productId and quantity
     */
    private List<OrderLineItemResponse> mapToItemResponse(List<OrderLineItem> items){
        return items.stream()
                .map(item -> new OrderLineItemResponse(
                        item.getProductId(),
                        item.getQuantity()
                )).toList();
    }
    //====================================================================

    /**
     * Converts a {@link CreateOrderRequest} into an {@link Order} entity.
     *
     * @param request the incoming order creation request
     * @return a new {@link Order} entity ready for persistence
     */
    public Order toEntity(CreateOrderRequest request) {
        // Map each item in the request to an OrderLineItem
        List<OrderLineItem> items = mapToOrderLineItems(request.items());
        // Construct and return the Order entity with the mapped items
        return new Order(items);
    }

    public OrderResponse toResponse(Order order) {
    List<OrderLineItemResponse> itemResponses = mapToItemResponse(
            order.getItems());

    return new OrderResponse(
            order.getId(),
            order.getCreatedAt(),
            order.getCustomerId(),
            itemResponses,
            order.getStatus(),
            order.getTotalPrice()
    );
    }
}
