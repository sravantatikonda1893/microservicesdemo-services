package com.microservicedemo.ordersservice.converter;

import com.microservicedemo.ordersservice.model.Order;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author sravantatikonda
 */
@Component
public class OrderWriterConverter implements Converter<Order, DBObject> {

  @Override
  public DBObject convert(final Order order) {
    final DBObject dbObject = new BasicDBObject();

    dbObject.put("orderCreatedDate", order.getOrderCreatedDate());
    dbObject.put("orderedById", order.getOrderedById());
    dbObject.put("orderZipCode", order.getOrderZipCode());
    dbObject.put("orderTotalAmount", order.getOrderTotalAmount());
    dbObject.put("orderTotalItems", order.getOrderTotalItems());
    dbObject.put("products", order.getProducts());
    dbObject.put("orderEmail", order.getOrderEmail());
    dbObject.put("orderStatus", order.getOrderStatus());

    dbObject.removeField("_class");
    return dbObject;
  }

}