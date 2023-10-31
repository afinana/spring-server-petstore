package net.petstore.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


/**
 * MapInventory
 */

@RedisHash("mapinventory")
@Data
public class MapInventory   {

  public MapInventory(){
    super();
  }

  @Id
  private String name = null;
  private Long value = null;

}

