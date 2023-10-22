package net.petstore.domain;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

/**
 * Order
 */
@Data
@Builder
@Document("order")
public class Order   {

  @Id
  private Long id = null;

  private Long petId = null;

  private Integer quantity = null;

  private OffsetDateTime shipDate = null;

  private OrderStatusEnum status = null;

  private Boolean complete = null;

}

