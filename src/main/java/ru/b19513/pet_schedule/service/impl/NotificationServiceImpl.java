package ru.b19513.pet_schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.b19513.pet_schedule.controller.entity.NotificationDTO;
import ru.b19513.pet_schedule.controller.entity.NotificationScheduleDTO;
import ru.b19513.pet_schedule.controller.entity.NotificationTimeoutDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.exceptions.NotFoundException;
import ru.b19513.pet_schedule.exceptions.WrongNotificationClassException;
import ru.b19513.pet_schedule.repository.GroupRepository;
import ru.b19513.pet_schedule.repository.NotificationNoteRepository;
import ru.b19513.pet_schedule.repository.NotificationRepository;
import ru.b19513.pet_schedule.repository.UserRepository;
import ru.b19513.pet_schedule.repository.entity.Notification;
import ru.b19513.pet_schedule.repository.entity.NotificationSchedule;
import ru.b19513.pet_schedule.repository.entity.NotificationTimeout;
import ru.b19513.pet_schedule.repository.entity.ScheduleTime;
import ru.b19513.pet_schedule.service.NotificationService;
import ru.b19513.pet_schedule.service.mapper.NotificationScheduleMapper;
import ru.b19513.pet_schedule.service.mapper.NotificationTimeoutMapper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.b19513.pet_schedule.consts.Consts.NOTIFICATION_DELETED;
import static ru.b19513.pet_schedule.consts.Consts.NOTIFICATION_NOTE_UPDATED;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationNoteRepository notificationNoteRepository;
    private final NotificationRepository notificationRepository;
    private final GroupRepository groupRepository;
    private final NotificationTimeoutMapper notificationTimeoutMapper;
    private final NotificationScheduleMapper notificationScheduleMapper;
    private final UserRepository userRepository;

    @Autowired
    public NotificationServiceImpl(NotificationNoteRepository notificationNoteRepository, NotificationRepository notificationRepository, GroupRepository groupRepository, NotificationTimeoutMapper notificationTimeoutMapper, NotificationScheduleMapper notificationScheduleMapper, UserRepository userRepository){

        this.notificationNoteRepository = notificationNoteRepository;
        this.notificationRepository = notificationRepository;
        this.groupRepository = groupRepository;
        this.notificationTimeoutMapper = notificationTimeoutMapper;
        this.notificationScheduleMapper = notificationScheduleMapper;
        this.userRepository = userRepository;
    }

    @Override
    public NotificationTimeoutDTO createNotificationTimeout(long groupId, String comment, long elapsed) {
        var group = groupRepository.findById(groupId).orElseThrow(NotFoundException::new);
        var notificationTimeout = NotificationTimeout.builder().elapsed(elapsed).build();
        notificationTimeout.setComment(comment); // у Notification нет builder, потому что он абстрактный
        notificationTimeout.setEnabled(true); // пришлось сетить
        // добавить в group новую Notification создать и вызвать сервис добавления в группу Notification ???
        return notificationTimeoutMapper.entityToDTO(notificationRepository.save(notificationTimeout));
    }

    @Override
    public NotificationScheduleDTO createNotificationSchedule(long groupId, String comment, List<LocalTime> times) {
        var group = groupRepository.findById(groupId).orElseThrow(NotFoundException::new);
        var scheduleTimeList = new ArrayList<ScheduleTime>();
        for (LocalTime time : times) {
            scheduleTimeList.add(ScheduleTime.builder().notifTime(time).build());
        }
        var notificationSchedule = NotificationSchedule.builder().times(scheduleTimeList).build();
        notificationSchedule.setComment(comment); // аналогично Timeout
        notificationSchedule.setEnabled(true);
        // добавить в group новую Notification создать и вызвать сервис добавления в группу Notification ???
        return notificationScheduleMapper.entityToDTO(notificationRepository.save(notificationSchedule));
    }

    @Override
    public NotificationScheduleDTO updateNotificationSchedule(NotificationScheduleDTO notif) {
        var notificationSchedule = notificationRepository.findById(notif.getId()).orElseThrow(NotFoundException::new);
        if (notificationSchedule instanceof NotificationSchedule){
            notificationScheduleMapper.updateEntity((NotificationSchedule) notificationSchedule, notif);
            return notificationScheduleMapper.entityToDTO((NotificationSchedule) notificationRepository.save(notificationSchedule));
        } else throw new WrongNotificationClassException();
    }

    @Override
    public NotificationTimeoutDTO updateNotificationTimeout(NotificationTimeoutDTO notif) {
        var notificationTimeout = notificationRepository.findById(notif.getId()).orElseThrow(NotFoundException::new);
        if (notificationTimeout instanceof NotificationTimeout){
            notificationTimeoutMapper.updateEntity((NotificationTimeout) notificationTimeout, notif);
            return notificationTimeoutMapper.entityToDTO((NotificationTimeout) notificationRepository.save(notificationTimeout));
        } else throw new WrongNotificationClassException();
    }

    @Override
    public List<NotificationDTO> showNotification(long userId) {
        var user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        var groupSet = user.getGroups();
        List<Notification> notificationList = new ArrayList<>();
        groupSet.forEach(g -> notificationList.addAll(g.getNotificationList()));
        // не совсем понял как сделать
        return null;
    }

    @Override
    public StatusDTO deleteNotification(long notifId) {
        var notification = notificationRepository.findById(notifId).orElseThrow(NotFoundException::new);
        notificationRepository.delete(notification);
        return StatusDTO.builder()
                .status(HttpStatus.OK)
                .description(NOTIFICATION_DELETED)
                .build();
    }

    @Override
    public StatusDTO setTimeInNotificationNote(long userId, List<Long> notificationsId) {
        var user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        for (var notifId : notificationsId){
            var notifNote = notificationNoteRepository.findByNotificationIdAndUser(notifId, user);
            notifNote.setLastTime(LocalDateTime.now());
            notificationNoteRepository.save(notifNote);
        }
        return StatusDTO.builder()
                .status(HttpStatus.OK)
                .description(NOTIFICATION_NOTE_UPDATED)
                .build();
    }
}
