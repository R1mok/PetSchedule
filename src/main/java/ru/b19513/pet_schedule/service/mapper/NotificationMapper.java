package ru.b19513.pet_schedule.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.b19513.pet_schedule.controller.entity.NotificationDTO;
import ru.b19513.pet_schedule.controller.entity.NotificationScheduleDTO;
import ru.b19513.pet_schedule.controller.entity.NotificationTimeoutDTO;
import ru.b19513.pet_schedule.repository.entity.Notification;
import ru.b19513.pet_schedule.repository.entity.NotificationSchedule;
import ru.b19513.pet_schedule.repository.entity.NotificationTimeout;


import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "times", expression = "java(entity.getTimes().forEach(t -> times.add(t.getNotifTime())))")
    NotificationScheduleDTO entityToDTO(NotificationSchedule entity);

    void updateEntity(@MappingTarget NotificationSchedule entity, NotificationScheduleDTO src);

    NotificationTimeoutDTO entityToDTO(NotificationTimeout entity);

    List<NotificationDTO> entityToDTO(Collection<Notification> entities);

    void updateEntity(@MappingTarget NotificationTimeout entity, NotificationTimeoutDTO src);
}
