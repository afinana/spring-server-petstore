package net.petstore.domain;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Category
 */
@Data
@Document("category")
public class Category   {
  public Category(){
    super();
  }

  @Id
  private Long id = null;

  private String name = null;

}

