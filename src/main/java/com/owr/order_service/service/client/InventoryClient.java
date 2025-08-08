package com.owr.order_service.service.client;


import com.owr.order_service.dto.request.OrderItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/*=================================================================================
 * Project: order-service
 * File: InventoryClient
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 10:54 AM
 * Description: REST client to communicate with Inventory-Service.
 * - Should verify stock availability before placing an order.
 =================================================================================*/
@Component
@RequiredArgsConstructor
public class InventoryClient {

    /**
     * The HTTP client used to send requests to external services.
     */
    private final RestTemplate restTemplate;

    /**
     * The base URL of the inventory service.
     * Injected from the application configuration using the property {@code inventory.service.port}.
     */
    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    /** ------------------------------------------------------------------
     * Helper functions
     ------------------------------------------------------------------*/
    /**
     * Fetches the available stock quantity for a given product from the external Inventory Service.
     *
     * @param productId the product identifier
     * @return the quantity available in stock
     */
    public Integer fetchAvailableQuantity(Long productId) {
        String url = inventoryServiceUrl + "/" + productId;
        return restTemplate.getForObject(url, Integer.class);
    }

    /** ------------------------------------------------------------------*/

    /**
     * Sends a GET request to the inventory-service to check stock availability.
     *
     * @param productId the ID of the product to check
     * @return true if the stock is available (quantity > 0), false otherwise
     */
    public boolean isStockSufficient(Long productId) {
        // Retrieve available quantity from the Inventory Service
        Integer quantity = fetchAvailableQuantity(productId);
        return quantity != null && quantity > 0;
    }

    /**
     * Sends a GET request to the inventory-service to retrieve the current stock quantity.
     *
     * @param productId the ID of the product to check
     * @return the current stock quantity, or 0 if unavailable
     */
    public int getStockQuantity(Long productId) {
        Integer quantity = fetchAvailableQuantity(productId);
        return quantity != null ? quantity : 0;
    }

    /**
     * Sends a POST request to the inventory-service to reduce the current stock quantity.
     *
     * @param productId the ID of the product to decrease
     * @param quantity  the quantity to reduce from inventory
     */
    public void decreaseStock(Long productId, int quantity) {
        // Get current stock from inventory service
        int currentStock = getStockQuantity(productId);

        if (currentStock < quantity ){
            throw  new IllegalArgumentException(
                    "Not enough stock to reduce for ProductId: " + productId
            );
        }

        // Calculate new Stock
        int newStock = currentStock - quantity;

        OrderItemRequest request = new OrderItemRequest(productId, newStock);

        // POST to inventory-service
        restTemplate.postForObject(
                inventoryServiceUrl,
                request,
                Void.class
        );
    }
}
