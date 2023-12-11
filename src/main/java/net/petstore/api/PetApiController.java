    package net.petstore.api;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import io.swagger.annotations.*;
    import lombok.extern.slf4j.Slf4j;
    import net.petstore.model.ModelApiResponse;
    import net.petstore.model.Pet;
    import net.petstore.security.annotation.AllowedRoles;
    import net.petstore.service.PetService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import javax.servlet.http.HttpServletRequest;
    import javax.validation.Valid;
    import javax.validation.constraints.NotNull;
    import java.util.List;

    @javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-05-06T17:38:50.285Z")
    @RestController
    @RequestMapping(value = "/v2")
    @Slf4j
    public class PetApiController implements PetApi {

        private final ObjectMapper objectMapper;

        private final HttpServletRequest request;

        @Autowired
        PetService petService;

        @org.springframework.beans.factory.annotation.Autowired
        public PetApiController(ObjectMapper objectMapper, HttpServletRequest request) {
            this.objectMapper = objectMapper;
            this.request = request;
        }



        @RequestMapping(value = "/pet",
                produces = { "application/json", "application/xml" },
                consumes = { "application/json", "application/xml" },
                method = RequestMethod.POST)
        @AllowedRoles("ADMIN")
        public ResponseEntity<Void> addPet(@ApiParam(value = "Pet object that needs to be added to the store" ,required=true )  @Valid @RequestBody Pet body) {

            log.info("addPet body={}", body);
            petService.addPet(body);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }



        @RequestMapping(value = "/pet/{petId}",
                produces = { "application/json", "application/xml" },
                method = RequestMethod.DELETE)
        @AllowedRoles("ADMIN")
        public ResponseEntity<Void> deletePet(@ApiParam(value = "Pet id to delete",required=true, example = "123") @PathVariable("petId") Long petId,@ApiParam(value = "Api Key") @RequestHeader(value="api_key", required=false) String apiKey){


            log.info("deletePet id={}", petId);
            petService.deletePet(petId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }


        @RequestMapping(value = "/pet/{petId}",
                produces = { "application/json", "application/xml" },
                method = RequestMethod.GET)
        @AllowedRoles("VISITOR")
       public ResponseEntity<Pet> getPetById(@ApiParam(value = "ID of pet to return",required=true, example = "123") @PathVariable("petId") Long petId) {


            try{

                log.info("getPetById id={}", petId);
                Pet pet = petService.getPetById(petId);
                log.info("getPetById result={}", pet);
                if (pet==null){
                    return new ResponseEntity<Pet>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<Pet>(pet,HttpStatus.OK);

            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Pet>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }


        @RequestMapping(value = "/pet",
                produces = { "application/json", "application/xml" },
                consumes = { "application/json", "application/xml" },
                method = RequestMethod.PUT)
        @AllowedRoles("ADMIN")
        public ResponseEntity<Void> updatePet(@ApiParam(value = "Pet object that needs to be added to the store" ,required=true )  @Valid @RequestBody Pet body){
            try{

                log.info("updatePet body={}", body);
                petService.updatePet(body);
                return new ResponseEntity<Void>(HttpStatus.OK);

            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @RequestMapping(value = "/pet/findByStatus",
                produces = { "application/json", "application/xml" },
                method = RequestMethod.GET)
        @AllowedRoles("VISITOR")
        public ResponseEntity<List<Pet>> findPetsByStatus(@NotNull @ApiParam(value = "Status values that need to be considered for filter", required = true, allowableValues = "available, pending, sold") @Valid @RequestParam(value = "status", required = true) List<String> status){


            try{

                log.info("findPetsByStatus status={}",status);

                List<Pet> result = petService.findPetsByStatus(status);
                return new ResponseEntity<List<Pet>>(result, HttpStatus.OK);

            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Pet>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }




        @RequestMapping(value = "/pet/findByTags",
                produces = { "application/json", "application/xml" },
                method = RequestMethod.GET)
        @AllowedRoles("VISITOR")
        public ResponseEntity<List<Pet>> findPetsByTags(@NotNull @ApiParam(value = "Tags to filter by", required = true) @Valid @RequestParam(value = "tags", required = true) List<String> tags){

            try{
                log.info("findPetsByTags tags={}", tags);
                List<Pet> result = petService.findPetsByTags(tags);

                return new ResponseEntity<List<Pet>>(result, HttpStatus.OK);

            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Pet>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        @RequestMapping(value = "/pet/{petId}",
                produces = { "application/json", "application/xml" },
                consumes = { "application/x-www-form-urlencoded" },
                method = RequestMethod.POST)
        @AllowedRoles("ADMIN")
        public ResponseEntity<Void> updatePetWithForm(@ApiParam(value = "ID of pet that needs to be updated",required=true, example = "123") @PathVariable("petId") Long petId,
                                               @ApiParam(value = "Updated name of the pet") @RequestParam(value="name", required=false)  String name,
                                               @ApiParam(value = "Updated status of the pet") @RequestParam(value="status", required=false)  String status) {


            return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
        }




        @ApiOperation(value = "uploads an image", nickname = "uploadFile", notes = "", response = ModelApiResponse.class, authorizations = {
                @Authorization(value = "petstore_auth", scopes = {
                        @AuthorizationScope(scope = "write:pets", description = "modify pets in your account"),
                        @AuthorizationScope(scope = "read:pets", description = "read your pets")
                })
        }, tags={ "pet", })
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "successful operation", response = ModelApiResponse.class) })
        @RequestMapping(value = "/pet/{petId}/uploadImage",
                produces = { "application/json" },
                consumes = { "multipart/form-data" },
                method = RequestMethod.POST)
        @AllowedRoles("ADMIN")
        public ResponseEntity<ModelApiResponse> uploadFile(@ApiParam(value = "ID of pet to update",required=true, example = "123") @PathVariable("petId") Long petId,
                                                    @ApiParam(value = "Additional data to pass to server") @RequestParam(value="additionalMetadata", required=false)  String additionalMetadata,
                                                    @ApiParam(value = "file to upload") @Valid @RequestPart(value="file", required=false) MultipartFile file){

            return new ResponseEntity<ModelApiResponse>(HttpStatus.NOT_IMPLEMENTED);
        }

    }
