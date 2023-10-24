package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * ModelApiResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-05-06T17:38:50.285Z")
@Data
public class ModelApiResponse   {

  public ModelApiResponse(){
    super();
  }

  @JsonProperty("code")
  @ApiModelProperty(value = "code")
  private Integer code = null;

  @JsonProperty("type")
  @ApiModelProperty(value = "type")
  private String type = null;

  @JsonProperty("message")
  @ApiModelProperty(value = "message")
  private String message = null;

  @Override
  public int hashCode() {
    return Objects.hash(code, type, message);
  }

}

