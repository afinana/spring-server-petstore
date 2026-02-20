/**
 * Petstore Store API
 */
package net.petstore.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.petstore.model.MapInventory;
import net.petstore.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;

@Validated
@Tag(name = "store", description = "the store API")
@RequestMapping(value = "/v2")
public interface StoreApi {

    @Operation(summary = "Delete purchase order by ID",
        description = "For valid response try integer IDs with positive integer value. Negative or non-integer values will generate API errors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Order not found") })
    @RequestMapping(value = "/store/order/{orderId}",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteOrder(@Min(1L) @Parameter(description = "ID of the order that needs to be deleted", required = true) @PathVariable("orderId") Long orderId);

    @Operation(summary = "Returns pet inventories by status",
        description = "Returns a map of status codes to quantities")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation") })
    @RequestMapping(value = "/store/inventory",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    ResponseEntity<List<MapInventory>> getInventory();

    @Operation(summary = "Find purchase order by ID",
        description = "For valid response try integer IDs with value >= 1 and <= 10. Other values will generated exceptions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Order not found") })
    @RequestMapping(value = "/store/order/{orderId}",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    ResponseEntity<Order> getOrderById(@Min(1L) @Max(10L) @Parameter(description = "ID of pet that needs to be fetched", required = true) @PathVariable("orderId") Long orderId);

    @Operation(summary = "Place an order for a pet")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid Order") })
    @RequestMapping(value = "/store/order",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Order> placeOrder(@Parameter(description = "order placed for purchasing the pet", required = true) @Valid @RequestBody Order body);
}
