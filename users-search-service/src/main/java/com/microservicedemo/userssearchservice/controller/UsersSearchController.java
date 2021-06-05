package com.microservicedemo.userssearchservice.controller;

import com.microservicedemo.userssearchservice.annotation.ApiPageable;
import com.microservicedemo.userssearchservice.dto.User;
import com.microservicedemo.userssearchservice.dto.mapper.UserProfileMapper;
import com.microservicedemo.userssearchservice.service.UsersSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author sravantatikonda
 */
@Slf4j
@RestController
@RequestMapping("search")
@Validated
@RequiredArgsConstructor
public class UsersSearchController {

  private final UsersSearchService usersSearchService;

  private final UserProfileMapper userProfileMapper;

  @ApiPageable
  @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<User>> findUsers(
      @ApiIgnore @PageableDefault final Pageable pageable) {

    usersSearchService.findUsers();

    return new ResponseEntity<>(userProfileMapper.mapAsPage(pageable, null),
        HttpStatus.OK);
  }
}
