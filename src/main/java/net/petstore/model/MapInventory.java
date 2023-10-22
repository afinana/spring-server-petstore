package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * MapInventory
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-05-06T17:38:50.285Z")
@Data
public class MapInventory   {
  @JsonProperty("name")
  @ApiModelProperty(value = "name")
  private String name = null;

  @JsonProperty("value")
  @ApiModelProperty(value = "value")
  private Long value = null;


  @Override
  public int hashCode() {
    return Objects.hash(name, value);
  }

}

