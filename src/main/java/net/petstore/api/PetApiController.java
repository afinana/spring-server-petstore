package net.petstore.api;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.petstore.model.ModelApiResponse;
import net.petstore.model.Pet;
import net.petstore.security.annotation.AllowedRoles;
import net.petstore.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/v2")
@Slf4j
@RequiredArgsConstructor
public class PetApiController implements PetApi {

    private final HttpServletRequest request;
    private final PetService petService;

    @RequestMapping(value = "/pet", produces = { "application/json", "application/xml" }, consumes = {
            "application/json", "application/xml" }, method = RequestMethod.POST)
    @AllowedRoles("VISITOR")
    public ResponseEntity<Void> addPet(
            @Parameter(description = "Pet object that needs to be added to the store", required = true) @Valid @RequestBody Pet body) {
        log.info("addPet body={}", body);
        petService.addPet(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/pet/{petId}", produces = { "application/json",
            "application/xml" }, method = RequestMethod.DELETE)
    @AllowedRoles("VISITOR")
    public ResponseEntity<Void> deletePet(
            @Parameter(description = "Pet id to delete", required = true) @PathVariable("petId") Long petId,
            @Parameter(description = "Api Key") @RequestHeader(value = "api_key", required = false) String apiKey) {
        log.info("deletePet id={}", petId);
        petService.deletePet(petId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/pet/{petId}", produces = { "application/json",
            "application/xml" }, method = RequestMethod.GET)
    @AllowedRoles("VISITOR")
    public ResponseEntity<Pet> getPetById(
            @Parameter(description = "ID of pet to return", required = true) @PathVariable("petId") Long petId) {
        try {
            log.info("getPetById id={}", petId);
            Pet pet = petService.getPetById(petId);
            log.info("getPetById result={}", pet);
            if (pet == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(pet, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/pet", produces = { "application/json", "application/xml" }, consumes = {
            "application/json", "application/xml" }, method = RequestMethod.PUT)
    @AllowedRoles("VISITOR")
    public ResponseEntity<Void> updatePet(
            @Parameter(description = "Pet object that needs to be added to the store", required = true) @Valid @RequestBody Pet body) {
        try {
            log.info("updatePet body={}", body);
            petService.updatePet(body);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/pet", produces = { "application/json", "application/xml" }, method = RequestMethod.GET)
    @AllowedRoles("VISITOR")
    public ResponseEntity<List<Pet>> getAllPets() {
        try {
            List<Pet> pets = petService.getAllPets();
            return new ResponseEntity<>(pets, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/pet/findByStatus", produces = { "application/json",
            "application/xml" }, method = RequestMethod.GET)
    @AllowedRoles("VISITOR")
    public ResponseEntity<List<Pet>> findPetsByStatus(
            @NotNull @Parameter(description = "Status values that need to be considered for filter", required = true) @Valid @RequestParam(value = "status") List<String> status) {
        try {
            log.info("findPetsByStatus status={}", status);
            return new ResponseEntity<>(petService.findPetsByStatus(status), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/pet/findByTags", produces = { "application/json",
            "application/xml" }, method = RequestMethod.GET)
    @AllowedRoles("VISITOR")
    public ResponseEntity<List<Pet>> findPetsByTags(
            @NotNull @Parameter(description = "Tags to filter by", required = true) @Valid @RequestParam(value = "tags") List<String> tags) {
        try {
            log.info("findPetsByTags tags={}", tags);
            return new ResponseEntity<>(petService.findPetsByTags(tags), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/pet/{petId}", produces = { "application/json", "application/xml" }, consumes = {
            "application/x-www-form-urlencoded" }, method = RequestMethod.POST)
    @AllowedRoles("VISITOR")
    public ResponseEntity<Void> updatePetWithForm(
            @Parameter(description = "ID of pet that needs to be updated", required = true) @PathVariable("petId") Long petId,
            @Parameter(description = "Updated name of the pet") @RequestParam(value = "name", required = false) String name,
            @Parameter(description = "Updated status of the pet") @RequestParam(value = "status", required = false) String status) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/pet/{petId}/uploadImage", produces = { "application/json" }, consumes = {
            "multipart/form-data" }, method = RequestMethod.POST)
    @AllowedRoles("VISITOR")
    public ResponseEntity<ModelApiResponse> uploadFile(
            @Parameter(description = "ID of pet to update", required = true) @PathVariable("petId") Long petId,
            @Parameter(description = "Additional data to pass to server") @RequestParam(value = "additionalMetadata", required = false) String additionalMetadata,
            @Parameter(description = "file to upload") @Valid @RequestPart(value = "file", required = false) MultipartFile file) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
