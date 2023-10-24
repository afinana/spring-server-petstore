package net.petstore.service;


import net.petstore.model.ModelApiResponse;
import net.petstore.model.Pet;
import net.petstore.repository.PetRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(net.petstore.api.PetApiController.class);

    public void addPet( Pet petDTO) {

        net.petstore.domain.Pet pet = convertToEntity(petDTO);
        petRepository.save(pet);

    }


    public void deletePet( Long petId ) {
        petRepository.deleteById(petId);

    }

    public List<Pet> findPetsByStatus(List<String> status) {
        return null;
    }

    public Pet getPetById(Long petId) {

        Optional<net.petstore.domain.Pet> pet = petRepository.findById(petId);
        Pet result = null;
        if (pet.isPresent()) {
            result = convertToDTO(pet.get());
        }
        return result;

    }

    public void updatePet(Pet petDto) {

        net.petstore.domain.Pet pet = convertToEntity(petDto);
        petRepository.save(pet);
    }

    public void updatePetWithForm( Long petId,
                                    String name,
                                  String status) {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");

    }

    public ModelApiResponse uploadFile( Long petId, String additionalMetadata, MultipartFile file) {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

    private net.petstore.domain.Pet convertToEntity(Pet petDto) {

       return modelMapper.map(petDto, net.petstore.domain.Pet.class);


    }
    private Pet convertToDTO(net.petstore.domain.Pet petEntity) {

        return modelMapper.map(petEntity, Pet.class);

    }


}
