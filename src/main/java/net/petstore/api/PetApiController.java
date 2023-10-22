package net.petstore.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import net.petstore.model.ModelApiResponse;
import net.petstore.model.Pet;
import net.petstore.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-05-06T17:38:50.285Z")

@Controller
public class PetApiController implements PetApi {

    private static final Logger log = LoggerFactory.getLogger(PetApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    PetService petService;

    @org.springframework.beans.factory.annotation.Autowired
    public PetApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> addPet(@ApiParam(value = "Pet object that needs to be added to the store" ,required=true )  @Valid @RequestBody Pet body) {
        String accept = request.getHeader("Accept");
        petService.addPet(body);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deletePet(@ApiParam(value = "Pet id to delete",required=true, example = "123") @PathVariable("petId") Long petId,@ApiParam(value = "0" ) @RequestHeader(value="api_key", required=false) String apiKey) {
        String accept = request.getHeader("Accept");
        petService.deletePet(petId);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Pet> getPetById(@ApiParam(value = "ID of pet to return",required=true, example = "123") @PathVariable("petId") Long petId) {
        String accept = request.getHeader("Accept");

        try{
            Pet pet = petService.getPetById(petId);
            return new ResponseEntity<Pet>(pet, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<Pet>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    public ResponseEntity<Void> updatePet(@ApiParam(value = "Pet object that needs to be added to the store" ,required=true )  @Valid @RequestBody Pet body) {
        try{
            petService.updatePet(body);
            return new ResponseEntity<Void>(HttpStatus.OK);

        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<List<Pet>> findPetsByStatus(@NotNull @ApiParam(value = "Status values that need to be considered for filter", required = true, allowableValues = "available, pending, sold") @Valid @RequestParam(value = "status", required = true) List<String> status) {


        return new ResponseEntity<List<Pet>>(HttpStatus.NOT_IMPLEMENTED);
    }


    public ResponseEntity<List<Pet>> findPetsByTags(@NotNull @ApiParam(value = "Tags to filter by", required = true) @Valid @RequestParam(value = "tags", required = true) List<String> tags) {


        return new ResponseEntity<List<Pet>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> updatePetWithForm(@ApiParam(value = "ID of pet that needs to be updated",required=true, example = "123") @PathVariable("petId") Long petId,@ApiParam(value = "Updated name of the pet") @RequestParam(value="name", required=false)  String name,@ApiParam(value = "Updated status of the pet") @RequestParam(value="status", required=false)  String status) {


        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ModelApiResponse> uploadFile(@ApiParam(value = "ID of pet to update",required=true, example = "123") @PathVariable("petId") Long petId,@ApiParam(value = "Additional data to pass to server") @RequestParam(value="additionalMetadata", required=false)  String additionalMetadata,@ApiParam(value = "file to upload") @Valid @RequestPart(value="file", required=false) MultipartFile file) {

        return new ResponseEntity<ModelApiResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
