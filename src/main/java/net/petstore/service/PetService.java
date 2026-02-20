package net.petstore.service;

import net.petstore.model.ModelApiResponse;
import net.petstore.model.Pet;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PetService {
    void addPet(Pet body);


    void deletePet(Long petId);

    List<Pet> findPetsByStatus(List<String> status);

    List<Pet> findPetsByTags(List<String> tags);

    List<Pet> getAllPets();


    Pet getPetById(Long petId);

    void updatePet(Pet body);

    void updatePetWithForm(Long petId,
                           String name,
                           String status);

    ModelApiResponse uploadFile(Long petId, String additionalMetadata, MultipartFile file);
}
