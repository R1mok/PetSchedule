package ru.b19513.pet_schedule.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.b19513.pet_schedule.controller.entity.UserDTO;
import ru.b19513.pet_schedule.repository.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO entityToDTO(User entity);

    List<UserDTO> entityToDTO(Collection<User> entities);

    @DoIgnore
    @Mapping(target = "invitations", ignore = true)
    @Mapping(target = "about", ignore = true)
    @Mapping(target = "gender", ignore = true)
    UserDTO entityToDTOConf(User entity);

    @DoIgnore
    default List<UserDTO> entityToDTOConf(Collection<User> entities) {
        List<UserDTO> toRet = new ArrayList<>();
        for (var us : entities)
            toRet.add(entityToDTOConf(us));
        return toRet;
    }
}
