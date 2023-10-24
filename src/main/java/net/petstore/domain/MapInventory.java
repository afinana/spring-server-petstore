package net.petstore.domain;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * MapInventory
 */
@Data
@Document("mapinventory")
public class MapInventory   {

  public MapInventory(){
    super();
  }

  @Id
  private String name = null;
  private Long value = null;

}

