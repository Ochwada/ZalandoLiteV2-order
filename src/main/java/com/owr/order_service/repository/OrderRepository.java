package com.owr.order_service.repository;


import com.owr.order_service.model.Order;
import com.owr.order_service.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

/*=================================================================================
 * Project: order-service
 * File: OrderRepository
 * Created by: Ochwada
 * Created on: 08, 8/8/2025, 11:37 AM
 * Description: Repository interface to interact with MongoDB Atlas.
 =================================================================================*/
public interface OrderRepository extends MongoRepository<Order, String> {
    /**
     * Repository interface for performing CRUD operations on {@link Order} documents
     * stored in MongoDB.
     */

    /**-------------------------------------------
     * Custom Methods
    -------------------------------------------*/
    /**
     * Finds all orders placed by a given customer.
     *
     * @param customerId the unique identifier of the customer
     * @return a list of {@link Order} documents associated with the specified customer
     */
    List<Order> findByCustomerId(String customerId);

    /**
     * Finds all orders with the specified status.
     *
     * @param status the {@link Status} enum representing the desired order status
     * @return a list of {@link Order} documents matching the given status
     */
    List<Order> findByStatus(Status status);


    /**
     * Finds all orders created within the given datetime range.
     *
     * @param start start of the date range (inclusive)
     * @param end   end of the date range (inclusive)
     * @return a list of {@link Order} documents created within the specified time frame
     */
    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);


}
