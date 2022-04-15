package ru.b19513.pet_schedule.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.b19513.pet_schedule.controller.entity.NotificationScheduleDTO;
import ru.b19513.pet_schedule.repository.entity.NotificationSchedule;


import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationScheduleMapper {
    NotificationScheduleDTO entityToDTO(NotificationSchedule entity);

    List<NotificationScheduleDTO> entityToDTO(Collection<NotificationSchedule> entities);

    void updateEntity(@MappingTarget NotificationSchedule entity, NotificationScheduleDTO src);
}
