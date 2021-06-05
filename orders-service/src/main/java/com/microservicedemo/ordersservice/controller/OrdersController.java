package com.microservicedemo.ordersservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author sravantatikonda
 */
@RestController
@Slf4j
public class OrdersController {

//  @Autowired
//  private OrderRepository orderRepository;
//
//  /**
//   * An API to create an order which will be inserted into a MongoDB, sends a notification to Kafka
//   * consumers for shipping and produuctcatalog/warehouse management
//   *
//   * @return
//   * @throws Exception
//   */
//  @ApiOperation(value = "createOrder", notes = "")
//  @PostMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
//  public ResponseEntity<Order> createOrder(@RequestBody Order order) throws Exception {
//
//    // Create Order
//    order.setOrderStatus("Created");
//    Order order1 = orderRepository.save(order);
//    log.info("Successfully created {} an order with order ID: {}", order.getOrderId());
//
//    //TODO: Process an order
//    return new ResponseEntity<>(order1, HttpStatus.OK);
//  }

//  /**
//   * An API to get an order with an orderId
//   *
//   * @return
//   * @throws Exception
//   */
//  @ApiOperation(value = "findOrderByOrderId", notes = "")
//  @GetMapping(value = "/order/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
//  public ResponseEntity<Order> findOrderByOrderId(@PathVariable String orderId)
//      throws Exception {
//
//    return new ResponseEntity<>(orderRepository.findOrderByOrderId(orderId),
//        HttpStatus.OK);
//  }
//
//  /**
//   * An API to get an order with an orderId
//   *
//   * @return
//   * @throws Exception
//   */
//  @ApiOperation(value = "findOrderByOrderId", notes = "")
//  @GetMapping(value = "/order/{orderedById}", produces = MediaType.APPLICATION_JSON_VALUE)
//  public ResponseEntity<List<Order>> findOrderByOrderId(@PathVariable Integer orderedById)
//      throws Exception {
//
//    return new ResponseEntity<>(orderRepository.findOrdersByOrderedById(orderedById),
//        HttpStatus.OK);
//  }
//
//  /**
//   * An API to get all orders within a zipcode
//   *
//   * @return
//   * @throws Exception
//   */
//  @ApiOperation(value = "findOrdersByOrderZipCode", notes = "")
//  @GetMapping(value = "/order/{orderZipCode}", produces = MediaType.APPLICATION_JSON_VALUE)
//  public ResponseEntity<List<Order>> findOrdersByOrderZipCode(@PathVariable String orderZipCode)
//      throws Exception {
//    return new ResponseEntity<>(orderRepository.findOrdersByOrderZipCode(orderZipCode),
//        HttpStatus.OK);
//  }
//
//  /**
//   * An API to cancel an order with an orderedById
//   *
//   * @return
//   * @throws Exception
//   */
//  @ApiOperation(value = "cancelOrder", notes = "")
//  @GetMapping(value = "/order/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
//  public ResponseEntity<Order> cancelOrder(@PathVariable String orderId) throws Exception {
//    Order order = orderRepository.findOrderByOrderId(orderId);
//    order.setOrderStatus("Cancelled");
//    return new ResponseEntity<>(orderRepository.cancelOrder(order), HttpStatus.OK);
//  }


}
