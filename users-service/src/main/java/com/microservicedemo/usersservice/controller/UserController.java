package com.microservicedemo.usersservice.controller;

import com.microservicedemo.usersservice.entity.User;
import com.microservicedemo.usersservice.model.UserDto;
import com.microservicedemo.usersservice.service.UserService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author sravantatikonda
 */
@RestController
@Slf4j
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * Updates a user record of a particular user
   *
   * @param request the value for {@link User}
   * @return the value for processed {@link User}
   */
  @ApiOperation(value = "updateUser", notes = "This API will return an updated user information")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Success"),
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 401, message = "Unauthorized"),
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 428, message = "Precondition Required")
  })
  @PutMapping(value = "user/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDto> updateUser(@RequestBody UserDto request) throws Exception {
    return new ResponseEntity<>(userService.updateUser(request),HttpStatus.OK);
  }

  /**
   * Registers a user record of a particular user
   *
   * @param request the value for {@link User}
   * @return the value for processed {@link User}
   */
  @ApiOperation(value = "registerProfile", notes = "This API will return a user information")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Success"),
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 401, message = "Unauthorized"),
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 428, message = "Precondition Required")
  })
  @PostMapping(value = "user/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDto> registerProfile(@RequestBody UserDto request) throws Exception {
    return new ResponseEntity<>(userService.registerUser(request), HttpStatus.OK);
  }

  /**
   * Gets a user record of a particular user
   *
   * @param userId
   * @return the value for processed {@link User}
   */
  @ApiOperation(value = "getProfile", notes = "This API will return a user information")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Success"),
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 401, message = "Unauthorized"),
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 428, message = "Precondition Required")
  })
  @GetMapping(value = "user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDto> getProfile(@PathVariable Integer userId) throws Exception {
    return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
  }

}
