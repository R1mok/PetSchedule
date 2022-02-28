package ru.b19513.pet_schedule.service.mapper;

import org.mapstruct.Mapper;
import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.repository.entity.Group;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDTO entityToDTO(Group entity);

    List<GroupDTO> entityToDTO(Collection<Group> entities);
}
