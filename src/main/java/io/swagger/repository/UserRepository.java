package io.swagger.repository;

import io.swagger.model.Pet;
import io.swagger.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public  interface UserRepository extends MongoRepository<User, String> {

}