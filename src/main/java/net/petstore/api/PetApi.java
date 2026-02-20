/**
 * Petstore Pet API
 */
package net.petstore.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.petstore.model.ModelApiResponse;
import net.petstore.model.Pet;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Validated
@Tag(name = "pet", description = "the pet API")
@RequestMapping(value = "/v2")
public interface PetApi {

    @Operation(summary = "Add a new pet to the store")
    @ApiResponses(value = { @ApiResponse(responseCode = "405", description = "Invalid input") })
    @RequestMapping(value = "/pet",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.POST)
    ResponseEntity<Void> addPet(@Parameter(description = "Pet object that needs to be added to the store", required = true) @Valid @RequestBody Pet body);

    @Operation(summary = "Deletes a pet")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Pet not found") })
    @RequestMapping(value = "/pet/{petId}",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deletePet(@Parameter(description = "Pet id to delete", required = true) @PathVariable("petId") Long petId,
                                   @Parameter(description = "Api Key") @RequestHeader(value = "api_key", required = false) String apiKey);

    @Operation(summary = "Finds Pets by status", description = "Multiple status values can be provided with comma separated strings")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid status value") })
    @RequestMapping(value = "/pet/findByStatus",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    ResponseEntity<List<Pet>> findPetsByStatus(@NotNull @Parameter(description = "Status values that need to be considered for filter", required = true) @Valid @RequestParam(value = "status", required = true) List<String> status);

    @Operation(summary = "Finds Pets by tags", description = "Multiple tags can be provided with comma separated strings.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid tag value") })
    @RequestMapping(value = "/pet/findByTags",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    ResponseEntity<List<Pet>> findPetsByTags(@NotNull @Parameter(description = "Tags to filter by", required = true) @Valid @RequestParam(value = "tags", required = true) List<String> tags);

    @Operation(summary = "Find pet by ID", description = "Returns a single pet")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Pet not found") })
    @RequestMapping(value = "/pet/{petId}",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    ResponseEntity<Pet> getPetById(@Parameter(description = "ID of pet to return", required = true) @PathVariable("petId") Long petId);

    @Operation(summary = "Update an existing pet")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Pet not found"),
        @ApiResponse(responseCode = "405", description = "Validation exception") })
    @RequestMapping(value = "/pet",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updatePet(@Parameter(description = "Pet object that needs to be added to the store", required = true) @Valid @RequestBody Pet body);

    @Operation(summary = "Updates a pet in the store with form data")
    @ApiResponses(value = { @ApiResponse(responseCode = "405", description = "Invalid input") })
    @RequestMapping(value = "/pet/{petId}",
        produces = { "application/json", "application/xml" },
        consumes = { "application/x-www-form-urlencoded" },
        method = RequestMethod.POST)
    ResponseEntity<Void> updatePetWithForm(@Parameter(description = "ID of pet that needs to be updated", required = true) @PathVariable("petId") Long petId,
                                           @Parameter(description = "Updated name of the pet") @RequestParam(value = "name", required = false) String name,
                                           @Parameter(description = "Updated status of the pet") @RequestParam(value = "status", required = false) String status);

    @Operation(summary = "uploads an image")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
    @RequestMapping(value = "/pet/{petId}/uploadImage",
        produces = { "application/json" },
        consumes = { "multipart/form-data" },
        method = RequestMethod.POST)
    ResponseEntity<ModelApiResponse> uploadFile(@Parameter(description = "ID of pet to update", required = true) @PathVariable("petId") Long petId,
                                                @Parameter(description = "Additional data to pass to server") @RequestParam(value = "additionalMetadata", required = false) String additionalMetadata,
                                                @Parameter(description = "file to upload") @Valid @RequestPart(value = "file", required = false) MultipartFile file);
}
