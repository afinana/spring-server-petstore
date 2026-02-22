package net.petstore.repository;

import lombok.extern.slf4j.Slf4j;
import net.petstore.model.Pet;
import net.petstore.model.PetStatusEnum;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class CustomItemRepositoryImpl implements CustomItemRepository {

    @Override
    public void updatePetQuantity(String name, PetStatusEnum status) {
        log.info("updatePetQuantity not supported currently");
    }

    @Override
    public List<Pet> findPetByName(String name) {
        return Collections.emptyList();
    }

    @Override
    public List<Pet> findCustomPetByTag(String tag) {
        return Collections.emptyList();
    }

}