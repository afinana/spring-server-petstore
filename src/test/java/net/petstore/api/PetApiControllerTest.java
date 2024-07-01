package net.petstore.api;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.petstore.api.PetApi;
import net.petstore.model.ModelApiResponse;
import net.petstore.model.Pet;
import net.petstore.service.PetService;
import org.springframework.web.multipart.MultipartFile;

public class PetApiControllerTest {

    @Mock
    private PetService petService;

    private PetApiController petApiController;

    @BeforeEach
    public void setUp() {
        petApiController = new PetApiController(null, null);
        petApiController.petService = petService;
    }

    @Test
    public void testAddPet() {
        Pet pet = new Pet();
        petApiController.addPet(pet);
        verify(petService).addPet(pet);
    }

    @Test
    public void testDeletePet() {
        Long petId = 123L;
        petApiController.deletePet(petId, null);
        verify(petService).deletePet(petId);
    }

    @Test
    public void testGetPetById_Found() {
        Long petId = 123L;
        Pet pet = new Pet();
        when(petService.getPetById(petId)).thenReturn(pet);

        ResponseEntity<Pet> response = petApiController.getPetById(petId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pet, response.getBody());
    }

    @Test
    public void testGetPetById_NotFound() {
        Long petId = 123L;
        when(petService.getPetById(petId)).thenReturn(null);

        ResponseEntity<Pet> response = petApiController.getPetById(petId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdatePet() {
        Pet pet = new Pet();
        petApiController.updatePet(pet);
        verify(petService).updatePet(pet);
    }

    @Test
    public void testFindPetsByStatus() {
        List<String> status = new ArrayList<>();
        status.add("available");
        petApiController.findPetsByStatus(status);
        verify(petService).findPetsByStatus(status);
    }

    @Test
    public void testFindPetsByTags() {
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        petApiController.findPetsByTags(tags);
        verify(petService).findPetsByTags(tags);
    }

    @Test
    public void testUpdatePetWithForm_NotImplemented() {
        Long petId = 123L;
        String name = "new name";
        String status = "sold";

        ResponseEntity<Void> response = petApiController.updatePetWithForm(petId, name, status);

        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    public void testUploadFile_NotImplemented() {
        Long petId = 123L;
        String additionalMetadata = "some data";
        MultipartFile file = null;

        ResponseEntity<ModelApiResponse> response = petApiController.uploadFile(petId, additionalMetadata, file);

        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }
}
