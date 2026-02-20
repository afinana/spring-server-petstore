package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * Tag
 */
@Validated
@Data
public class Tag   {

  public Tag(){
    super();
  }


  @Schema(description = "Tag ID")
  @JsonProperty("id")
  private Long id = null;

  @Schema(description = "Tag name")
  @JsonProperty("name")
  private String name = null;
}
