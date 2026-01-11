package net.petstore.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


/**
 * Tag
 */
@Data
@RedisHash("tag")
public class Tag   {

  public Tag(){
    super();
  }

  @Id
  private Long id = null;
  @Indexed
  private String name = null;

}

