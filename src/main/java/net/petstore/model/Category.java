package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * Category
 */
@Validated
@Data
public class Category   {

  public Category(){
    super();
  }

  @Schema(description = "Category ID")
  @JsonProperty("id")
  private Long id = null;

  @Schema(description = "Category name")
  @JsonProperty("name")
  private String name = null;
}
