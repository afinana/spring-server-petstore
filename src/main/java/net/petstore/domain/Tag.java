package net.petstore.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

