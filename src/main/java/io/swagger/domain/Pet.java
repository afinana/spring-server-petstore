package io.swagger.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Pet
 */
@Data
@Builder
@Document("pet")
public class Pet   {

  @Id
  private Long id = null;

  private Category category = null;

  private String name = null;

  private List<String> photoUrls = new ArrayList<String>();

  private List<Tag> tags = null;

  private PetStatusEnum status = null;

}

