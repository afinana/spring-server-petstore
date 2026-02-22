package net.petstore.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Tag
 */
@Data
@RedisHash("tag")
public class Tag {

  public Tag() {
    super();
  }

  @Id
  private Long id = null;
  private String name = null;

}
