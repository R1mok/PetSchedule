package ru.b19513.pet_schedule.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.b19513.pet_schedule.controller.entity.NotificationScheduleDTO;
import ru.b19513.pet_schedule.controller.entity.NotificationTimeoutDTO;
import ru.b19513.pet_schedule.repository.entity.Notification;
import ru.b19513.pet_schedule.repository.entity.NotificationSchedule;
import ru.b19513.pet_schedule.repository.entity.NotificationTimeout;

@Mapper
public interface NotificationMapper {
    @Mapping(target = "times", expression = "java(entity.getTimes().stream().map(t -> t.getNotifTime()).collect(java.util.stream.Collectors.toList()))")
    NotificationScheduleDTO entityToDTO(NotificationSchedule entity);

    @Mapping(target = "times", expression = "java(entity.getTimes())")
    void updateEntity(@MappingTarget NotificationSchedule entity, NotificationScheduleDTO src);

    NotificationTimeoutDTO entityToDTO(NotificationTimeout entity);

    void updateEntity(@MappingTarget NotificationTimeout entity, NotificationTimeoutDTO src);

}
