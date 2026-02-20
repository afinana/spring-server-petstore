package net.petstore.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.petstore.mapper.PetMapper;
import net.petstore.model.ModelApiResponse;
import net.petstore.model.Pet;
import net.petstore.repository.PetRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {


    private final PetRepository petRepository;
    private final PetMapper petMapper;

    public void addPet(Pet petDTO) {
        net.petstore.domain.Pet pet = petMapper.toEntity(petDTO);
        petRepository.save(pet);
    }

    @CacheEvict(cacheNames = "pets", key = "#petId")
    public void deletePet(Long petId) {
        petRepository.deleteById(petId);
    }

    public List<Pet> findPetsByTags(List<String> tags) {
        return tags.stream()
                .flatMap(tag -> petRepository.findWithTags(tag).stream())
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<Pet> findPetsByStatus(List<String> statusList) {
        return statusList.stream()
                .flatMap(statusCode -> {
                    net.petstore.domain.PetStatusEnum statusEnum = net.petstore.domain.PetStatusEnum.fromValue(statusCode);
                    return petRepository.findPetByStatus(statusEnum).stream();
                })
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "pets", key = "#petId")
    public Pet getPetById(Long petId) {
        Optional<net.petstore.domain.Pet> pet = petRepository.findById(petId);
        return pet.map(petMapper::toDto).orElse(null);
    }

    @CacheEvict(cacheNames = "pets", key = "#petDto.id")
    public void updatePet(Pet petDto) {
        net.petstore.domain.Pet pet = petMapper.toEntity(petDto);
        petRepository.save(pet);
    }

    public void updatePetWithForm(Long petId, String name, String status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ModelApiResponse uploadFile(Long petId, String additionalMetadata, MultipartFile file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
