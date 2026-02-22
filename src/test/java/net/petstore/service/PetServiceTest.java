package net.petstore.service;

import net.petstore.domain.PetStatusEnum;
import net.petstore.mapper.PetMapper;
import net.petstore.model.Pet;
import net.petstore.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetMapper petMapper;

    @InjectMocks
    private PetServiceImpl petService;

    // domain entity (stored in Redis)
    private net.petstore.domain.Pet domainPet;

    // model DTO (returned by the API)
    private Pet modelPet;

    @BeforeEach
    void setUp() {
        domainPet = new net.petstore.domain.Pet();
        domainPet.setId(1L);
        domainPet.setName("Buddy");
        domainPet.setStatus(PetStatusEnum.AVAILABLE);

        modelPet = new Pet();
        modelPet.setId(1L);
        modelPet.setName("Buddy");
        modelPet.setStatus(net.petstore.model.PetStatusEnum.AVAILABLE);
    }

    @Test
    void getAllPets_shouldReturnAllPets() {
        when(petRepository.findAll()).thenReturn(List.of(domainPet));
        when(petMapper.toDto(domainPet)).thenReturn(modelPet);

        List<Pet> result = petService.getAllPets();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Buddy");
        verify(petRepository).findAll();
    }

    @Test
    void getPetById_whenExists_shouldReturnPet() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(domainPet));
        when(petMapper.toDto(domainPet)).thenReturn(modelPet);

        Pet result = petService.getPetById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Buddy");
    }

    @Test
    void getPetById_whenNotExists_shouldReturnNull() {
        when(petRepository.findById(99L)).thenReturn(Optional.empty());

        Pet result = petService.getPetById(99L);

        assertThat(result).isNull();
    }

    @Test
    void addPet_shouldSaveDomainEntity() {
        when(petMapper.toEntity(modelPet)).thenReturn(domainPet);

        petService.addPet(modelPet);

        verify(petMapper).toEntity(modelPet);
        verify(petRepository).save(domainPet);
    }

    @Test
    void deletePet_shouldCallRepositoryDeleteById() {
        petService.deletePet(1L);

        verify(petRepository).deleteById(1L);
    }

    @Test
    void findPetsByStatus_shouldReturnMatchingPets() {
        when(petRepository.findByStatus(PetStatusEnum.AVAILABLE)).thenReturn(List.of(domainPet));
        when(petMapper.toDto(domainPet)).thenReturn(modelPet);

        List<Pet> result = petService.findPetsByStatus(List.of("available"));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo(net.petstore.model.PetStatusEnum.AVAILABLE);
    }

    @Test
    void findPetsByStatus_withUnknownStatus_shouldReturnEmpty() {
        // PetStatusEnum.fromValue("unknown") returns null; repo returns empty list for
        // null status
        when(petRepository.findByStatus(null)).thenReturn(List.of());

        List<Pet> result = petService.findPetsByStatus(List.of("unknown"));

        assertThat(result).isEmpty();
    }

    @Test
    void findPetsByTags_shouldReturnMatchingPets() {
        net.petstore.domain.Tag cuteTag = new net.petstore.domain.Tag();
        cuteTag.setName("cute");
        domainPet.setTags(List.of(cuteTag));
        when(petRepository.findAll()).thenReturn(List.of(domainPet));
        when(petMapper.toDto(domainPet)).thenReturn(modelPet);

        List<Pet> result = petService.findPetsByTags(List.of("cute"));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Buddy");
    }

    @Test
    void updatePet_shouldSaveUpdatedEntity() {
        when(petMapper.toEntity(modelPet)).thenReturn(domainPet);

        petService.updatePet(modelPet);

        verify(petMapper).toEntity(modelPet);
        verify(petRepository).save(domainPet);
    }
}
