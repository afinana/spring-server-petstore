package net.petstore.mapper;

import net.petstore.domain.PetStatusEnum;
import net.petstore.mapper.CategoryMapper;
import net.petstore.mapper.TagMapper;
import net.petstore.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * MapStruct mapper for converting between Pet domain entity and Pet DTO model.
 * Compile-time generated — zero reflection overhead compared to ModelMapper.
 */
@Mapper(componentModel = "spring", uses = { CategoryMapper.class, TagMapper.class })
public interface PetMapper {

    @Mapping(source = "status", target = "status", qualifiedByName = "statusToModel")
    Pet toDto(net.petstore.domain.Pet entity);

    @Mapping(source = "status", target = "status", qualifiedByName = "statusToDomain")
    net.petstore.domain.Pet toEntity(Pet dto);

    List<Pet> toDtoList(List<net.petstore.domain.Pet> entities);

    @Named("statusToModel")
    default net.petstore.model.PetStatusEnum statusToModel(PetStatusEnum status) {
        if (status == null) return null;
        return net.petstore.model.PetStatusEnum.fromValue(status.toString());
    }

    @Named("statusToDomain")
    default PetStatusEnum statusToDomain(net.petstore.model.PetStatusEnum status) {
        if (status == null) return null;
        return PetStatusEnum.fromValue(status.toString());
    }
}

