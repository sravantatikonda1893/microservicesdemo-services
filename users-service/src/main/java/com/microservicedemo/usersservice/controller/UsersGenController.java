package com.microservicedemo.usersservice.controller;

import com.microservicedemo.usersservice.generator.UsersGenerator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sravantatikonda
 */
@Slf4j
@RestController
public class UsersGenController {

 @Autowired
 private UsersGenerator usersGenerator;

 /**
  * An API to generate user records in DB and order records in XML files
  *
  * @param usersCount
  * @return
  * @throws Exception
  */
 @ApiOperation(value = "loadUsers", notes = "An API to generate user records in DB and order records in XML files")
 @GetMapping(value = "/loader/{usersCount}", produces = MediaType.APPLICATION_JSON_VALUE)
 @ApiResponses(value = {
     @ApiResponse(code = 201, message = "Success"),
     @ApiResponse(code = 400, message = "Bad Request"),
     @ApiResponse(code = 401, message = "Unauthorized"),
     @ApiResponse(code = 404, message = "Not Found"),
     @ApiResponse(code = 428, message = "Precondition Required")
 })
 public ResponseEntity<Boolean> loadUsers(@PathVariable Integer usersCount) throws Exception {
  usersGenerator.loadCSVValues();
  usersGenerator.loadUserTable(usersCount);
  log.info("Successfully created {} users", usersCount);
  return new ResponseEntity<>(true, HttpStatus.OK);
 }
}
