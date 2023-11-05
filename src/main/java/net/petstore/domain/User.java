package net.petstore.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * User
 */
@Data
@RedisHash("user")
public class User   {

  public User(){
    super();
  }
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

