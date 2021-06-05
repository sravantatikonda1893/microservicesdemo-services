package com.microservicedemo.product.model;

import lombok.Data;

/**
 * @author sravantatikonda
 */
@Data
public class Product {

  private String productId;

  private String productName;

  private Double productCost;

  private String productCategory;

  private String productDescription;
}
