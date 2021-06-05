package com.microservicedemo.ordersservice.repository;

import com.microservicedemo.ordersservice.model.Order;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author sravantatikonda
 */
//public interface OrderRepository extends MongoRepository<Order, String>,
//    QuerydslPredicateExecutor<Order> {
//
//  @Query("{ 'orderId' : ?0 }")
//  Order findOrderByOrderId(String orderId);
//
//  @Query("{ 'orderedById' : ?0 }")
//  List<Order> findOrdersByOrderedById(Integer orderedById);
//
//  @Query("{ 'orderZipCode' : ?0 }")
//  List<Order> findOrdersByOrderZipCode(String orderZipCode);
//
//  Order cancelOrder(Order order);
//
//  //TODO: Find all orders with a specific productId
//
//}