package com.microservicedemo.product.controller;

import com.microservicedemo.product.util.ProductsGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author sravantatikonda
 */
@RestController
@Slf4j
@RequestMapping("load/")
public class ProductsLoadController {

  @Autowired
  private ProductsGenerator productsGenerator;

  /**
   * An API to load products to an XML file
   *
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "loadProducts", notes = "")
  @GetMapping(value = "product/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> loadProducts() throws Exception {
    productsGenerator.loadCSVValues();
    productsGenerator.genProductXmlFile();
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

}
