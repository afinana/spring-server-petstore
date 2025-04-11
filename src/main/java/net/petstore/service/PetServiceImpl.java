package net.petstore.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.petstore.model.ModelApiResponse;
import net.petstore.model.Pet;
import net.petstore.repository.CustomPetRepository;
import net.petstore.repository.PetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetServiceImpl implements PetService {


    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomPetRepository customPetRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public void addPet(Pet petDTO) {

        try {
            String petJson = objectMapper.writeValueAsString(petDTO);
            kafkaTemplate.send("pet-topic", "CREATE", petJson);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish pet event", e);
        }

    }

    @Override
    public void deletePet(Long petId) {
        try {
            kafkaTemplate.send("pet-topic", "DELETE", String.valueOf(petId));
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish pet event", e);
        }

    }

    @Override
    public List<Pet> findPetsByTags(List<String> tags) {


        List<Pet> petArrayList = new ArrayList<>();

        for (String myTag : tags) {
            // Query mongodb
           // List<net.petstore.domain.Pet> domainPets = customPetRepository.findCustomPetByTag(myTag);
            List<net.petstore.domain.Pet> domainPets = petRepository.findByTags_Name(myTag);

            // Convert domain query result to DTO list
            for (net.petstore.domain.Pet domainPet : domainPets) {
                petArrayList.add(convertToDTO(domainPet));
            }
        }
        return petArrayList;
    }

    @Override
    public List<Pet> findPetsByStatus(List<String> statusList) {

        ArrayList<Pet> petArrayList = new ArrayList<>();
        for (String statusCode : statusList) {

            net.petstore.domain.PetStatusEnum statusEnum = net.petstore.domain.PetStatusEnum.fromValue(statusCode);

            if (statusEnum==null){
                throw new IllegalArgumentException("status parameter should be a valid value");
            }

            // Query redis db
            List<net.petstore.domain.Pet> domainPets = petRepository.findByStatus(statusEnum);
            for (net.petstore.domain.Pet domainPet : domainPets) {

                // Convert domain query result to DTO list
                petArrayList.add(convertToDTO(domainPet));
            }
        }
        return petArrayList;
    }

    @Override
    public Pet getPetById(Long petId) {

        Optional<net.petstore.domain.Pet> pet = petRepository.findById(petId);
        Pet result = null;
        if (pet.isPresent()) {
            result = convertToDTO(pet.get());
        }
        return result;

    }

    @Override
    public void updatePet(Pet petDto) {

        try {
            String petJson = objectMapper.writeValueAsString(petDto);
            kafkaTemplate.send("pet-topic", "UPDATE", petJson);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish pet event", e);
        }
    }

    @Override
    public void updatePetWithForm(Long petId,
                                  String name,
                                  String status) {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public ModelApiResponse uploadFile(Long petId, String additionalMetadata, MultipartFile file) {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }


    private net.petstore.domain.Pet convertToEntity(Pet petDto) {

        return modelMapper.map(petDto, net.petstore.domain.Pet.class);


    }


    private Pet convertToDTO(net.petstore.domain.Pet petEntity) {

        return modelMapper.map(petEntity, Pet.class);

    }


}
