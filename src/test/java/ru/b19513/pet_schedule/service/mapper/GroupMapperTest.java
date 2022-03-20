package ru.b19513.pet_schedule.service.mapper;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.repository.entity.Group;

class GroupMapperTest {
    private final GroupMapper mapper = Mappers.getMapper(GroupMapper.class);

    @Test
    void updateEntity() {
        val EXCEPTED = Group.builder()
                .name("lol")
                .description("kek")
                .build();
        var group = Group.builder()
                .name("lel")
                .description("kek")
                .build();
        var upd = GroupDTO.builder()
                .name("lol")
                .build();
        mapper.updateEntity(group, upd);
        Assertions.assertEquals(EXCEPTED.getName(), group.getName());
        Assertions.assertEquals(EXCEPTED.getDescription(), group.getDescription());
    }
}