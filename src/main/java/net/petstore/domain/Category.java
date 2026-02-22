package net.petstore.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Category
 */
@Data
@RedisHash("category")
public class Category {
  public Category() {
    super();
  }

  @Id
  private Long id = null;

  private String name = null;

}
