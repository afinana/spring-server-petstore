package net.petstore.mapper;

import net.petstore.model.Tag;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for Tag.
 */
@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag toDto(net.petstore.domain.Tag entity);
    net.petstore.domain.Tag toEntity(Tag dto);
}

