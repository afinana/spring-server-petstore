package net.petstore.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.petstore.model.Pet;
import net.petstore.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PetApiControllerTest {

    @InjectMocks
    private PetApiController petApiController;

    @Mock
    private PetService petService;


    @Mock
    private ObjectMapper objectMapper;

    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        petApiController = new PetApiController(objectMapper, request);
    }

    @Test
    void testAddPet() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");

        doNothing().when(petService).addPet(pet);

        ResponseEntity<Void> response = petApiController.addPet(pet);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(petService, times(1)).addPet(pet);
    }

    @Test
    void testDeletePet() {
        Long petId = 1L;

        doNothing().when(petService).deletePet(petId);

        ResponseEntity<Void> response = petApiController.deletePet(petId, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(petService, times(1)).deletePet(petId);
    }

    @Test
    void testGetPetById() {
        Long petId = 1L;
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName("Buddy");

        when(petService.getPetById(petId)).thenReturn(pet);

        ResponseEntity<Pet> response = petApiController.getPetById(petId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pet, response.getBody());
        verify(petService, times(1)).getPetById(petId);
    }

    @Test
    void testUpdatePet() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");

        doNothing().when(petService).updatePet(pet);

        ResponseEntity<Void> response = petApiController.updatePet(pet);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(petService, times(1)).updatePet(pet);
    }

    @Test
    void testFindPetsByStatus() {
        List<String> status = Arrays.asList("available");
        List<Pet> pets = Arrays.asList(new Pet(1L, "Buddy"), new Pet(2L, "Max"));

        when(petService.findPetsByStatus(status)).thenReturn(pets);

        ResponseEntity<List<Pet>> response = petApiController.findPetsByStatus(status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pets, response.getBody());
        verify(petService, times(1)).findPetsByStatus(status);
    }

    @Test
    void testFindPetsByTags() {
        List<String> tags = Arrays.asList("friendly", "small");
        List<Pet> pets = Arrays.asList(new Pet(1L, "Buddy"), new Pet(2L, "Max"));

        when(petService.findPetsByTags(tags)).thenReturn(pets);

        ResponseEntity<List<Pet>> response = petApiController.findPetsByTags(tags);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pets, response.getBody());
        verify(petService, times(1)).findPetsByTags(tags);
    }

    // Additional test cases for unimplemented methods can be added here
}