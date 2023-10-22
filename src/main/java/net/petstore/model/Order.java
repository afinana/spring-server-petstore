package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Order
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-05-06T17:38:50.285Z")
@Data
public class Order   {

  @JsonProperty("id")
  @ApiModelProperty(value = "0L")
  private Long id = null;

  @JsonProperty("petId")
  @ApiModelProperty(value = "0L")
  private Long petId = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  @JsonProperty("shipDate")
  @ApiModelProperty(value = "ship date")
 private OffsetDateTime shipDate = null;



  @JsonProperty("status")
  @ApiModelProperty(value = "Order Status")
  private OrderStatusEnum status = null;

  @JsonProperty("complete")
  private Boolean complete = null;

 @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(this.id, order.id) &&
        Objects.equals(this.petId, order.petId) &&
        Objects.equals(this.quantity, order.quantity) &&
        Objects.equals(this.shipDate, order.shipDate) &&
        Objects.equals(this.status, order.status) &&
        Objects.equals(this.complete, order.complete);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, petId, quantity, shipDate, status, complete);
  }

}

