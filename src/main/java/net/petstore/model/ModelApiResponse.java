package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * ModelApiResponse
 */
@Validated
@Data
public class ModelApiResponse   {

  public ModelApiResponse(){
    super();
  }

  @Schema(description = "Response code")
  @JsonProperty("code")
  private Integer code = null;

  @Schema(description = "Response type")
  @JsonProperty("type")
  private String type = null;

  @Schema(description = "Response message")
  @JsonProperty("message")
  private String message = null;
}
