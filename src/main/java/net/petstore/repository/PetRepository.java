package net.petstore.repository;

import net.petstore.domain.Pet;
import net.petstore.domain.PetStatusEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public  interface PetRepository extends CrudRepository<Pet, Long> {


    List<Pet> findByStatus(PetStatusEnum statusEnum);

    List<Pet> findByTags_Name(String myTag);
}