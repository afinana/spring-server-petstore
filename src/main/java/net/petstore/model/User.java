package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * User
 */
@Validated
@Data
public class User   {

  public User(){
    super();
  }

  @Schema(description = "User ID")
  @JsonProperty("id")
  private Long id = null;

  @Schema(description = "Username")
  @JsonProperty("username")
  private String username = null;

  @Schema(description = "First name")
  @JsonProperty("firstName")
  private String firstName = null;

  @Schema(description = "Last name")
  @JsonProperty("lastName")
  private String lastName = null;

  @Schema(description = "Email address")
  @JsonProperty("email")
  private String email = null;

  @Schema(description = "Password")
  @JsonProperty("password")
  private String password = null;

  @Schema(description = "Phone number")
  @JsonProperty("phone")
  private String phone = null;

  @Schema(description = "User Status")
  @JsonProperty("userStatus")
  private Integer userStatus = null;
}
