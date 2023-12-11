package net.petstore.repository;


import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import net.petstore.model.Pet;
import net.petstore.model.PetStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class  CustomItemRepositoryImpl implements CustomItemRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public void updatePetQuantity(String name, PetStatusEnum status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));

        Update update = new Update();
        update.set("status", status);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Pet.class);

        if  (result == null) {
            log.info("No documents updated");
        }else {
            log.info(result.getModifiedCount() + " document(s) updated..");
        }

    }

    public List<Pet> findPetByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));

       return mongoTemplate.find(query, Pet.class);

    }

    public List<Pet> findCustomPetByTag(String tag) {

        ArrayList<String> tagsArray = new ArrayList<>();
        tagsArray.add(tag);

        Query query = new Query();
        query.addCriteria(Criteria.where("array").all(tagsArray));

        return mongoTemplate.find(query, Pet.class);

    }


}