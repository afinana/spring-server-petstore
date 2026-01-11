package net.petstore.api;

import net.petstore.model.ModelApiResponse;
import net.petstore.model.Pet;
import net.petstore.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class PetApiControllerTest {

    @Mock
    private PetService petService;

    private PetApiController petApiController;

    @BeforeEach
    public void setUp() {
        petApiController = new PetApiController(null, null);
        petApiController.petService = petService;
    }

    @Test

    void testAddPet() {

        Pet pet = new Pet();
        petApiController.addPet(pet);
        verify(petService).addPet(pet);
    }

    @Test

    void testDeletePet() {

        Long petId = 123L;
        petApiController.deletePet(petId, null);
        verify(petService).deletePet(petId);
    }

    @Test

    void testGetPetById_Found() {

        Long petId = 123L;
        Pet pet = new Pet();
        when(petService.getPetById(petId)).thenReturn(pet);

        ResponseEntity<Pet> response = petApiController.getPetById(petId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pet, response.getBody());
    }

    @Test

    void testGetPetById_NotFound() {

        Long petId = 123L;
        when(petService.getPetById(petId)).thenReturn(null);

        ResponseEntity<Pet> response = petApiController.getPetById(petId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdatePet() {
        Pet pet = new Pet();

        petApiController.updatePet(pet);
        verify(petService).updatePet(pet);
    }

    void testFindPetsByStatus() {
        List<String> status = new ArrayList<>();
        status.add("available");

        petApiController.findPetsByStatus(status);
        verify(petService).findPetsByStatus(status);
    }

    @Test
    void testFindPetsByTags() {
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        petApiController.findPetsByTags(tags);
        verify(petService).findPetsByTags(tags);
    }

    @Test
    void testUpdatePetWithForm_NotImplemented() {

        Long petId = 123L;
        String name = "new name";
        String status = "sold";

        ResponseEntity<Void> response = petApiController.updatePetWithForm(petId, name, status);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test

    void testUploadFile_NotImplemented() {

        Long petId = 123L;
        String additionalMetadata = "some data";
        MultipartFile file = null;

        ResponseEntity<ModelApiResponse> response = petApiController.uploadFile(petId, additionalMetadata, file);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }
}