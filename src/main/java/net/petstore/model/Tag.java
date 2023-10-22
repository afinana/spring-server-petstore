package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Tag
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-05-06T17:38:50.285Z")
@Data
public class Tag   {
  @JsonProperty("id")
  @ApiModelProperty(value = "0L")
  private Long id = null;

  @JsonProperty("name")
  @ApiModelProperty(value = "name" )
  private String name = null;

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

}

