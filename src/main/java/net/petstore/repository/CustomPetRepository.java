package net.petstore.repository;


import net.petstore.model.Pet;
import net.petstore.model.PetStatusEnum;
import net.petstore.domain.Pet;
import java.util.List;

public interface CustomPetRepository {

    void updatePetQuantity(String name, PetStatusEnum status);

    List<Pet> findPetByName(String name);

    List<Pet> findCustomPetByTag(String tag);


}
