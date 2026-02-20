package net.petstore.repository;

import net.petstore.domain.Pet;
import net.petstore.domain.PetStatusEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public  interface PetRepository extends MongoRepository<Pet, Long> {

    @Query("{ 'name' : ?0 }")
    List<Pet> findPetByName(String name);

    @Query("{ 'name' : { $regex: ?0 } }")
    List<Pet> findPetsByRegexpName(String regexp);


    @Query(value = "{ 'tags': { $elemMatch: { 'name' : ?0 }}}")
    List<Pet> findWithTags(String tag);

    @Query(value = "{ 'status': ?0 }")
    List<Pet> findPetByStatus(PetStatusEnum statusEnum);


}