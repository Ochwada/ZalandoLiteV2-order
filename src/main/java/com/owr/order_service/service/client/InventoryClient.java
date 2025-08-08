package com.owr.order_service.service.client;


import com.owr.order_service.dto.request.InventoryUpdateRequest;
import com.owr.order_service.dto.request.OrderItemRequest;
import com.owr.order_service.dto.response.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

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
     * The HTTP client used to send requests to the external Inventory Service.
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
     * <p>
     * The request is authenticated using a Bearer token and expects the inventory service
     * to return a JSON array of {@link InventoryResponse} entries.
     * *
     * The method sums the quantities for the specified product.
     *
     * @param productId the product identifier
     * @param token     the JWT bearer token used for authorization
     * @return the total available quantity for the specified product, or 0 if unavailable
     */
    public Integer fetchAvailableQuantity(Long productId, String token) {
        // Construct the URL to the inventory-service endpoint
        String url = inventoryServiceUrl + "/" + productId;

        // Set up HTTP headers with Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Adds: Authorization: Bearer <token>

        // Wrap headers in an HttpEntity (no body needed for GET)
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Send GET request and expect an array of InventoryResponse
        ResponseEntity<InventoryResponse[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                InventoryResponse[].class
        );

        // Parse the response body
        InventoryResponse[] inventoryEntries = response.getBody();

        // Return 0 if no entries or response is null
        if (inventoryEntries == null || inventoryEntries.length == 0) {
            return 0;
        }

        // Sum up all quantities in the response
        return Arrays.stream(inventoryEntries)
                .mapToInt(InventoryResponse::quantity)
                .sum();
    }


    /** ------------------------------------------------------------------*/

    /**
     * Checks whether a product has sufficient stock.
     *
     * @param productId the product ID
     * @param token     JWT bearer token
     * @return true if stock > 0, false otherwise
     */
    public boolean isStockSufficient(Long productId, String token) {
        // Retrieve available quantity from the Inventory Service
        Integer quantity = fetchAvailableQuantity(productId, token);
        return quantity != null && quantity > 0;
    }

    /**
     * Gets stock quantity from the inventory service with authentication.
     *
     * @param productId the product ID
     * @param token     the Bearer token for authentication
     * @return available stock or 0 if unavailable
     */
    public int getStockQuantity(Long productId, String token) {
        try {
            String url = inventoryServiceUrl + "/" + productId;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);  // Add Authorization: Bearer <token>
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Integer> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Integer.class
            );

            return response.getBody() != null ? response.getBody() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Sends a POST request to the inventory service to reduce the current stock quantity.
     *
     * @param productId the ID of the product to decrease
     * @param quantity  the quantity to reduce from inventory
     * @param token     the Bearer token for authentication
     */
    public void decreaseStock(Long productId, int quantity, String token) {
        // Get current stock with authentication
        int currentStock = getStockQuantity(productId, token);

        if (currentStock < quantity) {
            throw new IllegalArgumentException(
                    "Not enough stock to reduce for ProductId: " + productId
            );
        }

        // Calculate new stock
        int newStock = currentStock - quantity;

        InventoryUpdateRequest request = new InventoryUpdateRequest(productId, newStock);

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);  // Authorization: Bearer <token>
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InventoryUpdateRequest> entity = new HttpEntity<>(request, headers);

        // Send POST to inventory-service
        restTemplate.postForEntity(inventoryServiceUrl, entity, Void.class);
    }

}
