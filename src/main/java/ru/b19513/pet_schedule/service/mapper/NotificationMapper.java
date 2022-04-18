package ru.b19513.pet_schedule.service.mapper;

import org.mapstruct.Mapper;
import ru.b19513.pet_schedule.controller.entity.NotificationDTO;
import ru.b19513.pet_schedule.repository.entity.Notification;


import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    List<NotificationDTO> entityToDTO(Collection<Notification> entities);
}
