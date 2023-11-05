package net.petstore.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

/**
 * Pet
 */
@Data
@RedisHash("pet")
public class Pet   {

  public Pet(){
    super();
  }

  @Id
  private Long id = null;
  private Category category = null;
  private String name = null;
  private List<String> photoUrls = new ArrayList<String>();

  @Indexed
  private List<Tag> tags = null;

  @Indexed
  private PetStatusEnum status = null;

}

