package net.petstore.mapper;

import net.petstore.model.Category;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for Category.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toDto(net.petstore.domain.Category entity);
    net.petstore.domain.Category toEntity(Category dto);
}

