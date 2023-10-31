package net.petstore.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.OffsetDateTime;

/**
 * Order
 */
@Data
@RedisHash("order")
public class Order   {
  public Order(){
    super();
  }

  @Id
  private Long id = null;
  private Long petId = null;
  private Integer quantity = null;
  private OffsetDateTime shipDate = null;
  private OrderStatusEnum status = null;
  private Boolean complete = null;

}

