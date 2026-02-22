package net.petstore.repository;

import net.petstore.domain.Pet;
import net.petstore.domain.PetStatusEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PetRepository extends CrudRepository<Pet, Long> {

    List<Pet> findByName(String name);

    List<Pet> findByStatus(PetStatusEnum status);

}