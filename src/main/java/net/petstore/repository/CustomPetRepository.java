package net.petstore.repository;

import net.petstore.domain.Pet;

import java.util.List;

public interface CustomPetRepository {

    List<Pet> findCustomPetByTag(String tag);


}
