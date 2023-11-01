package net.petstore.repository;


import lombok.extern.slf4j.Slf4j;
import net.petstore.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class  CustomPetRepositoryImpl implements CustomPetRepository {

    @Autowired
    RedisTemplate redisTemplate;



    public List<Pet> findCustomPetByTag(String tag) {

        return null;

    }


}