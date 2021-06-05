package com.microservicedemo.ordersservice.controller;

import com.microservicedemo.ordersservice.util.OrdersGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author sravantatikonda
 */
@RestController
@Slf4j
@RequestMapping("generate/")
public class OrdersGenerateController {

  @Autowired
  private OrdersGenerator ordersGenerator;

  /**
   * An API to generate orders records in XML file/s
   *
   * @param filesCount
   * @param recordCount
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "loadOrders", notes = "")
  @GetMapping(value = "order/load/{filesCount}/{recordCount}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> loadOrders(@PathVariable Integer filesCount,
      @PathVariable Integer recordCount) throws Exception {
    ordersGenerator.loadCSVValues();
    ordersGenerator.genOrderXmlFiles(filesCount, recordCount);
    log.info("Successfully created {} order files with {} orders in each order file", filesCount,
        recordCount);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }
}
