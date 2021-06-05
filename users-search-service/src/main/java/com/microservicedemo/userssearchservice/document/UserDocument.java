package com.microservicedemo.userssearchservice.document;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


/**
 * @author sravantatikonda
 */
@Data
@Document(indexName = "profile", type = "userProfile")
@AllArgsConstructor
@NoArgsConstructor
public class UserDocument implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Integer userId;

  private String firstName;

  private String lastName;

  private String email;

  private String city;

  private String state;

  private String country;

  private String zipCode;

  private java.util.Date dateOfBirth;
}
