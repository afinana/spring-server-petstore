package net.petstore.repository;

import net.petstore.model.Pet;

import java.util.List;

public interface CustomPetRepository {

    public List<Pet> findCustomPetByTag(String tag);


}
