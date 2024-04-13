package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-05-06T17:38:50.285Z")
@Data
public class User   {

  public User(){
    super();
  }
  @JsonProperty("id")
  @ApiModelProperty(value = "0L")
  private Long id = null;


  @JsonProperty("username")
  @ApiModelProperty(value = "name")
  private String userName = null;

  @JsonProperty("firstName")
  @ApiModelProperty(value = "firstName")
  private String firstName = null;

  @JsonProperty("lastName")
  @ApiModelProperty(value = "lastName")
  private String lastName = null;

  @JsonProperty("email")
  @ApiModelProperty(value = "email")
  private String email = null;

  @JsonProperty("password")
  @ApiModelProperty(value = "password")
  private String password = null;

  @JsonProperty("phone")
  @ApiModelProperty(value = "phone")
  private String phone = null;

  @JsonProperty("userStatus")
  @ApiModelProperty(value = "userStatus")
  private Integer userStatus = null;



  @Override
  public int hashCode() {
    return Objects.hash(id, userName, firstName, lastName, email, password, phone, userStatus);
  }

}

