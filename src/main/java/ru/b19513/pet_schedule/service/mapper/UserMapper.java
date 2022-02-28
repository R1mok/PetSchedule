package ru.b19513.pet_schedule.service.mapper;

import org.mapstruct.Mapper;
import ru.b19513.pet_schedule.controller.entity.UserDTO;
import ru.b19513.pet_schedule.repository.entity.User;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO entityToDTO(User entity);

    List<UserDTO> entityToDTO(Collection<User> entities);
}
