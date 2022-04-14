package ru.b19513.pet_schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.b19513.pet_schedule.controller.entity.NotificationDTO;
import ru.b19513.pet_schedule.controller.entity.NotificationScheduleDTO;
import ru.b19513.pet_schedule.controller.entity.NotificationTimeoutDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.exceptions.NotFoundException;
import ru.b19513.pet_schedule.repository.GroupRepository;
import ru.b19513.pet_schedule.repository.NotificationNoteRepository;
import ru.b19513.pet_schedule.repository.NotificationRepository;
import ru.b19513.pet_schedule.repository.entity.NotificationTimeout;
import ru.b19513.pet_schedule.service.NotificationService;

import java.time.LocalTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationNoteRepository notificationNoteRepository;
    private final NotificationRepository notificationRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public NotificationServiceImpl(NotificationNoteRepository notificationNoteRepository, NotificationRepository notificationRepository, GroupRepository groupRepository){

        this.notificationNoteRepository = notificationNoteRepository;
        this.notificationRepository = notificationRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public NotificationTimeoutDTO createNotificationTimeout(long groupId, String comment, long elapsed) {
        var group = groupRepository.findById(groupId).orElseThrow(NotFoundException::new);
        var notificationTimeout = NotificationTimeout.builder().elapsed(elapsed).build();
        notificationTimeout.setComment(comment); // у Notification нет builder, потому что он абстрактный
        notificationTimeout.setEnabled(true); // пришлось сетить
        // добавить в group новую Notification
        // return mapEntityToDTO
        return null;
    }

    @Override
    public NotificationScheduleDTO createNotificationSchedule(long groupId, String comment, List<LocalTime> times) {
        return null;
    }

    @Override
    public NotificationScheduleDTO updateNotificationSchedule(NotificationScheduleDTO notif) {
        return null;
    }

    @Override
    public NotificationTimeoutDTO updateNotificationTimeout(NotificationTimeoutDTO notif) {
        return null;
    }

    @Override
    public List<NotificationDTO> showNotification(long userId) {
        return null;
    }

    @Override
    public NotificationDTO deleteNotification(long notifId) {
        return null;
    }

    @Override
    public StatusDTO setTimeInNotificationNote(long userId, List<Long> notificationsId) {
        return null;
    }
}
