package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * MapInventory
 */
@Validated
@Data
public class MapInventory   {

  public MapInventory(){
    super();
  }

  @Schema(description = "Name")
  @JsonProperty("name")
  private String name = null;

  @Schema(description = "Value")
  @JsonProperty("value")
  private Long value = null;
}
