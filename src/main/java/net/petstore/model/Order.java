package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;

/**
 * Order
 */
@Validated
@Data
public class Order   {

    public Order(){
        super();
    }

    @Schema(description = "Order ID")
    @JsonProperty("id")
  private Long id = null;

    @Schema(description = "Pet ID")
    @JsonProperty("petId")
  private Long petId = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  @Schema(description = "Ship date")
  @JsonProperty("shipDate")
 private OffsetDateTime shipDate = null;



    @Schema(description = "Order Status")
  @JsonProperty("status")
  private OrderStatusEnum status = null;

  @JsonProperty("complete")
  private Boolean complete = null;
}
