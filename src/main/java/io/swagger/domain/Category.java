package io.swagger.domain;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Category
 */
@Data
@Builder
@Document("category")
public class Category   {

  @Id
  private Long id = null;

  private String name = null;

}

