package io.swagger.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 * Tag
 */
@Data
@Builder
@Document("tag")
public class Tag   {

  @Id
  private Long id = null;

  private String name = null;

}

