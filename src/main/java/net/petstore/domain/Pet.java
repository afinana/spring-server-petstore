package net.petstore.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Pet
 */
@Data
@Document("pets")
public class Pet   {

  public Pet(){
    super();
  }

  @Id
  private Long id = null;
  private Category category = null;
  private String name = null;
  private List<String> photoUrls = new ArrayList<String>();
  private List<Tag> tags = null;
  private PetStatusEnum status = null;

}

