package com.owr.order_service.service.client;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/*=================================================================================
 * Project: order-service
 * File: ProductClient
 * Created by: Ochwada
 * Created on: 11, 8/11/2025, 4:20 PM
 * Description: 
 =================================================================================*/
@Component
@RequiredArgsConstructor
public class ProductClient {
    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public Double getUnitPrice(Long productId, String token) {
        String url = productServiceUrl + "/" + productId + "/price";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Double> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Double.class
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new IllegalStateException("Product price GET failed for productId=" + productId);
        }
        return response.getBody();
    }
}
