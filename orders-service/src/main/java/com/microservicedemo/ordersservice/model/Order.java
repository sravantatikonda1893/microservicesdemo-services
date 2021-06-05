package com.microservicedemo.ordersservice.model;

import com.querydsl.core.annotations.QueryEntity;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

/**
 * @author sravantatikonda
 */
@Data
@QueryEntity
@Document
public class Order {

  @Id
  @Indexed
  private String orderId;

  @Indexed(direction = IndexDirection.DESCENDING)
  private ZonedDateTime orderCreatedDate;

  @Indexed
  private Integer orderedById;

  @Indexed
  private String orderZipCode;

  private Double orderTotalAmount;

  private Integer orderTotalItems;

  //List of productIds
  //Make sure the productId passed is consistent
  private List<String> products;

  private String orderEmail;

  private String orderStatus;

  @PersistenceConstructor
  public Order(String orderId, ZonedDateTime orderCreatedDate, Integer orderedById,
      String orderZipCode, Double orderTotalAmount, Integer orderTotalItems,
      List<String> products, String orderEmail, String orderStatus) {
    this.orderId = orderId;
    this.orderCreatedDate = orderCreatedDate;
    this.orderedById = orderedById;
    this.orderZipCode = orderZipCode;
    this.orderTotalAmount = orderTotalAmount;
    this.orderTotalItems = orderTotalItems;
    this.products = products;
    this.orderEmail = orderEmail;
    this.orderStatus = orderStatus;
  }
}
