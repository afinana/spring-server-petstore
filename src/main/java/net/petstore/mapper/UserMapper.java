package net.petstore.mapper;

import net.petstore.model.User;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for User.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDto(net.petstore.domain.User entity);
    net.petstore.domain.User toEntity(User dto);
}

