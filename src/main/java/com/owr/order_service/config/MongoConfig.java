package com.owr.order_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.client.RestTemplate;

/*=================================================================================
 * Project: order-service
 * File: MongoConfig
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 10:57 AM
 * Description: Configuration class that enables auditing support for MongoDB
 =================================================================================*/
@Configuration
@EnableMongoAuditing
public class MongoConfig {
    /**
     * By adding the {@code @EnableMongoAuditing} annotation, Spring Data MongoDB will automatically populate fields
     * annotated with {@code @CreatedDate} and {@code @LastModifiedDate} during entity persistence.
     * *
     * This class does not require any methods or fields; its presence in the application context is sufficient for
     * activating auditing behavior.
     */

    /** --------------------------------------------------------------
     * Creates and registers a {@link RestTemplate} bean.
     *
     * @return a new instance of {@code RestTemplate}
     */

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
