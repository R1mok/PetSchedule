package ru.b19513.pet_schedule.service;

import ru.b19513.pet_schedule.controller.entity.NotificationDTO;
import ru.b19513.pet_schedule.controller.entity.NotificationScheduleDTO;
import ru.b19513.pet_schedule.controller.entity.NotificationTimeoutDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;

import java.sql.Time;
import java.util.List;

public interface NotificationService {
    NotificationTimeoutDTO createNotificationTimeout(long groupId, String comment, long elapsed);

    NotificationScheduleDTO createNotificationSchedule(long groupId, String comment, List<Time> times);

    NotificationScheduleDTO updateNotificationSchedule( NotificationScheduleDTO notif);

    NotificationTimeoutDTO updateNotificationTimeout( NotificationTimeoutDTO notif);

    List<NotificationDTO> showNotification(long userId);

    NotificationDTO deleteNotification( long notifId);

    StatusDTO setTimeInNotificationNote(long userId, List<Long> notificationsId);
}
