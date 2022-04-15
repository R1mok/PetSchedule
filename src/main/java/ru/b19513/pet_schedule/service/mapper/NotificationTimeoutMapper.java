package ru.b19513.pet_schedule.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.b19513.pet_schedule.controller.entity.NotificationTimeoutDTO;
import ru.b19513.pet_schedule.repository.entity.NotificationTimeout;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationTimeoutMapper {
    NotificationTimeoutDTO entityToDTO(NotificationTimeout entity);

    List<NotificationTimeoutDTO> entityToDTO(Collection<NotificationTimeout> entities);

    void updateEntity(@MappingTarget NotificationTimeout entity, NotificationTimeoutDTO src);
}
