package net.petstore.repository;


import lombok.extern.slf4j.Slf4j;
import net.petstore.domain.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Slf4j
@Service
public class  CustomPetRepositoryImpl implements CustomPetRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @Override
    public List<Pet> findCustomPetByTag(String tag) {

        // search pets by tags Name using redisTemplate
        List<Pet> petList = new ArrayList<>();
        String key="pet:tags.name:"+tag;

        // get all pet ids by tag name using redisTemplate
        Set<Object> ids = redisTemplate.opsForSet().members(key);


        // get all pets by ids using redisTemplate
        if (ids == null) {
            return petList;
        }
        // convert ids to string
        List<String> idList = new ArrayList<>();
        for (Object id : ids) {
            String idStr = "pet:"+id.toString();
            idList.add(idStr);
        }
        // for each pet id of idList get pet object
        for (Object id : idList) {
            Map petMap = (Map) redisTemplate.opsForValue().get("pet:"+id);
            Pet pet = new ObjectMapper().convertValue(petMap, Pet.class);
            petList.add(pet);
        }



        /*
        List<Object> pets = redisTemplate.opsForValue().multiGet(idList);
        // convert pets to Pet object
        if (pets == null) {
            return petList;
        }
        for (Object petMap : pets) {
            Pet pet = new ObjectMapper().convertValue(petMap, Pet.class);
            petList.add(pet);
        }
        */
        log.info("pets found by tag name: {}", petList);
        return petList;

    }


}