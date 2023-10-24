package net.petstore.repository;

import net.petstore.domain.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public  interface PetRepository extends MongoRepository<Pet, Long> {

    public List<Pet> findByName(String firstName);

}