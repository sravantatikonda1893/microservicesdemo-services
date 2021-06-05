package com.microservicedemo.userssearchservice.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author sravantatikonda
 */
@Data
public class User implements Serializable {

  private static final long serialVersionUID = -3707819200740751550L;

  private Integer userId;

  private String firstName;

  private String lastName;

  private String email;

  private String city;

  private String state;

  private String country;

  private String zipCode;

  private Date dateOfBirth;
}
