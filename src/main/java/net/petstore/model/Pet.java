package net.petstore.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Pet
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-05-06T17:38:50.285Z")
@Data
public class Pet   {

  public Pet(){
    super();
  }

  @ApiModelProperty(value = "0L")
  @JsonProperty("id")
  private Long id = null;

  @ApiModelProperty(value = "category")
  @Valid
  @JsonProperty("category")
  private Category category = null;


  @ApiModelProperty(example = "doggie", required = true, value = "")
  @NotNull
  @JsonProperty("name")
  private String name = null;

  @ApiModelProperty(required = true, value = "")
  @NotNull
  @JsonProperty("photoUrls")
  @Valid
  private List<String> photoUrls = new ArrayList<String>();

  @ApiModelProperty(value = "tags")
  @JsonProperty("tags")
  @Valid
  private List<Tag> tags = null;


  @ApiModelProperty(value = "pet status in the store")
  @JsonProperty("status")
  private PetStatusEnum status = null;


}

