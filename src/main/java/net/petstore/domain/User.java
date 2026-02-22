package net.petstore.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * User
 */
@Data
@RedisHash("users")
public class User {

  public User() {
    super();
  }

  @Id
  private Long id = null;
  @Indexed
  private String username = null;
  private String firstName = null;
  private String lastName = null;
  @Indexed
  private String email = null;
  private String password = null;
  private String phone = null;
  private Integer userStatus = null;
}
