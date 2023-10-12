package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Category
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-05-06T17:38:50.285Z")
@Data
public class Category   {
  @JsonProperty("id")
  @ApiModelProperty(value = "Id")
  private Long id = null;

  @JsonProperty("name")
  @ApiModelProperty(value = "Name")
  private String name = null;

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

}

