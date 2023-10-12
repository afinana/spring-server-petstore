package io.swagger.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 * User
 */
@Data
@Document("user")
public class User   {

  @Id
  private Long id = null;

  private String username = null;

  private String firstName = null;

  private String lastName = null;

  private String email = null;

  private String password = null;

  private String phone = null;

  private Integer userStatus = null;
}

