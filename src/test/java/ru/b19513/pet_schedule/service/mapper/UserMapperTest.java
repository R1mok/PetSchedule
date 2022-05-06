package ru.b19513.pet_schedule.service.mapper;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.b19513.pet_schedule.controller.entity.UserDTO;
import ru.b19513.pet_schedule.repository.entity.User;
import ru.b19513.pet_schedule.repository.entity.enums.Gender;

import java.util.List;

class UserMapperTest {
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void entityToDTO() {
        val users = List.of(
                User.builder().id(0).login("Vantuz").name("Иван").gender(Gender.MALE).password("42").build(),
                User.builder().id(1).login("Jawa").about("Поделил на ноль").name("Джава").gender(Gender.MALE).password("43").build()
        );
        val excepted = List.of(
                UserDTO.builder().id(0).login("Vantuz").name("Иван").gender(ru.b19513.pet_schedule.controller.entity.enums.Gender.MALE).build(),
                UserDTO.builder().id(1).login("Jawa").about("Поделил на ноль").name("Джава").gender(ru.b19513.pet_schedule.controller.entity.enums.Gender.MALE).build()
        );
        var usersDTO = mapper.entityToDTO(users);
        Assertions.assertIterableEquals(excepted, usersDTO);
    }
}