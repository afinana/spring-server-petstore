package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Pet
 */
@Validated
@Data
public class Pet {

  public Pet() {
    super();
  }

  @Schema(description = "Pet ID", example = "0")
  @JsonProperty("id")
  private Long id = null;

  @Schema(description = "category")
  @Valid
  @JsonProperty("category")
  private Category category = null;

  @Schema(description = "Name of the pet", example = "doggie", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  @JsonProperty("name")
  private String name = null;

  @Schema(description = "Photo URLs", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  @JsonProperty("photoUrls")
  @Valid
  private List<String> photoUrls = new ArrayList<String>();

  @Schema(description = "tags")
  @JsonProperty("tags")
  @Valid
  private List<Tag> tags = null;

  @Schema(description = "pet status in the store")
  @JsonProperty("status")
  private PetStatusEnum status = null;

}
