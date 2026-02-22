package net.petstore.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.petstore.model.Pet;
import net.petstore.model.PetStatusEnum;
import net.petstore.service.PetService;
import net.petstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import net.petstore.security.config.SecurityConfig;

/**
 * Slice tests for PetApiController.
 * SecurityConfig permits all requests, so no authentication setup is needed.
 * UserService is mocked to prevent Spring from trying to load MongoDB
 * repositories
 * that are not available in a WebMvcTest slice.
 */
@WebMvcTest(PetApiController.class)
@Import(SecurityConfig.class)
@SuppressWarnings("null")
class PetApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    // Mocked to avoid MongoDB repository wiring in the web slice
    @MockBean
    @SuppressWarnings("unused")
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pet pet;

    @BeforeEach
    void setUp() {
        pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");
        pet.setPhotoUrls(List.of("http://example.com/buddy.jpg"));
        pet.setStatus(PetStatusEnum.AVAILABLE);
    }

    // ─── GET /v2/pet ────────────────────────────────────────────────────────────

    @Test
    void getAllPets_shouldReturn200WithList() throws Exception {
        when(petService.getAllPets()).thenReturn(List.of(pet));

        mockMvc.perform(get("/v2/pet").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Buddy"))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getAllPets_whenNoPets_shouldReturnEmptyList() throws Exception {
        when(petService.getAllPets()).thenReturn(List.of());

        mockMvc.perform(get("/v2/pet").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    // ─── GET /v2/pet/{petId} ─────────────────────────────────────────────────────

    @Test
    void getPetById_whenExists_shouldReturn200() throws Exception {
        when(petService.getPetById(1L)).thenReturn(pet);

        mockMvc.perform(get("/v2/pet/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    void getPetById_whenNotFound_shouldReturn404() throws Exception {
        when(petService.getPetById(99L)).thenReturn(null);

        mockMvc.perform(get("/v2/pet/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // ─── POST /v2/pet ────────────────────────────────────────────────────────────

    @Test
    void addPet_shouldReturn200() throws Exception {
        doNothing().when(petService).addPet(any(Pet.class));

        mockMvc.perform(post("/v2/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isOk());

        verify(petService).addPet(any(Pet.class));
    }

    // ─── PUT /v2/pet ─────────────────────────────────────────────────────────────

    @Test
    void updatePet_shouldReturn200() throws Exception {
        doNothing().when(petService).updatePet(any(Pet.class));

        mockMvc.perform(put("/v2/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isOk());

        verify(petService).updatePet(any(Pet.class));
    }

    // ─── DELETE /v2/pet/{petId} ──────────────────────────────────────────────────

    @Test
    void deletePet_shouldReturn200() throws Exception {
        doNothing().when(petService).deletePet(1L);

        mockMvc.perform(delete("/v2/pet/1"))
                .andExpect(status().isOk());

        verify(petService).deletePet(1L);
    }

    // ─── GET /v2/pet/findByStatus ────────────────────────────────────────────────

    @Test
    void findPetsByStatus_shouldReturn200WithFilteredList() throws Exception {
        when(petService.findPetsByStatus(List.of("available"))).thenReturn(List.of(pet));

        mockMvc.perform(get("/v2/pet/findByStatus")
                .param("status", "available")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("available"));
    }

    @Test
    void findPetsByStatus_whenNoneFound_shouldReturnEmptyList() throws Exception {
        when(petService.findPetsByStatus(List.of("sold"))).thenReturn(List.of());

        mockMvc.perform(get("/v2/pet/findByStatus")
                .param("status", "sold")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // ─── GET /v2/pet/findByTags ──────────────────────────────────────────────────

    @Test
    void findPetsByTags_shouldReturn200WithMatchingPets() throws Exception {
        when(petService.findPetsByTags(List.of("cute"))).thenReturn(List.of(pet));

        mockMvc.perform(get("/v2/pet/findByTags")
                .param("tags", "cute")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Buddy"));
    }
}
