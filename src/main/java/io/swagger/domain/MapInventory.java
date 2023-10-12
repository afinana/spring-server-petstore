package io.swagger.domain;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * MapInventory
 */
@Data
@Builder
@Document("mapinventory")
public class MapInventory   {
  @Id
  private String name = null;

  private Long value = null;


}

