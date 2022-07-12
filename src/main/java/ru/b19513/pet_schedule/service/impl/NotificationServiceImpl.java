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
import ru.b19513.pet_schedule.repository.*;
import ru.b19513.pet_schedule.repository.entity.*;
import ru.b19513.pet_schedule.service.NotificationService;
import ru.b19513.pet_schedule.service.mapper.NotificationMapper;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.b19513.pet_schedule.consts.Consts.NOTIFICATION_DELETED;
import static ru.b19513.pet_schedule.consts.Consts.NOTIFICATION_NOTE_UPDATED;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationNoteRepository notificationNoteRepository;
    private final NotificationRepository notificationRepository;
    private final GroupRepository groupRepository;
    private final PetRepository petRepository;
    private final FeedNoteRepository feedNoteRepository;
    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationServiceImpl(NotificationNoteRepository notificationNoteRepository, NotificationRepository notificationRepository, GroupRepository groupRepository, PetRepository petRepository, FeedNoteRepository feedNoteRepository, NotificationMapper notificationMapper) {

        this.notificationNoteRepository = notificationNoteRepository;
        this.notificationRepository = notificationRepository;
        this.groupRepository = groupRepository;
        this.petRepository = petRepository;
        this.feedNoteRepository = feedNoteRepository;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public NotificationTimeoutDTO createNotificationTimeout(long groupId, long petId, String comment, long elapsed) {
        var group = groupRepository.findById(groupId)
                .orElseThrow(new NotFoundException("Group with group id " + groupId + " not found"));
        var pet = petRepository.findById(petId)
                .orElseThrow(new NotFoundException("Pet with pet id " + petId + " not found"));
        var notificationTimeout = NotificationTimeout.builder()
                .elapsed(elapsed)
                .group(group)
                .pet(pet)
                .comment(comment)
                .enabled(true)
                .build();
        var notifSet = pet.getNotifications(); // добавляю к питомцу созданное уведомление
        if (notifSet == null) {
            notifSet = new HashSet<>();
        }
        notifSet.add(notificationTimeout);
        pet.setNotifications(notifSet);
        if (group.getNotificationList() == null) {
            group.setNotificationList(new ArrayList<>());
        }
        group.getNotificationList().addAll(notifSet);
        var notifInRepo = notificationRepository.save(notificationTimeout);
        return notificationMapper.entityToDTO(notifInRepo);
    }

    @Override
    public NotificationScheduleDTO createNotificationSchedule(long groupId, long petId, String comment, List<LocalTime> times) {
        var group = groupRepository.findById(groupId)
                .orElseThrow(new NotFoundException("Group with group id " + groupId + " not found"));
        var pet = petRepository.findById(petId)
                .orElseThrow(new NotFoundException("Pet with pet id " + petId + " not found"));
        var scheduleTimeList = new ArrayList<ScheduleTime>();
        for (LocalTime time : times) {
            scheduleTimeList.add(ScheduleTime.builder().notifTime(time).build());
        }
        var notificationSchedule = NotificationSchedule.builder()
                .times(scheduleTimeList)
                .group(group)
                .comment(comment)
                .enabled(true)
                .pet(pet)
                .build();
        var notifSet = pet.getNotifications(); // добавляю к питомцу созданное уведомление
        if (notifSet == null) {
            notifSet = new HashSet<>();
        }
        notifSet.add(notificationSchedule);
        pet.setNotifications(notifSet);
        var notifInRepo = notificationRepository.save(notificationSchedule);
        return notificationMapper.entityToDTO(notifInRepo);
    }

    @Override
    public NotificationScheduleDTO updateNotificationSchedule(NotificationScheduleDTO notif) {
        var notificationSchedule = notificationRepository.findById(notif.getId())
                .orElseThrow(new NotFoundException("Notification with notification id " + notif.getId() + " not found"));
        if (notificationSchedule instanceof NotificationSchedule) {
            notificationMapper.updateEntity((NotificationSchedule) notificationSchedule, notif);
            return notificationMapper.entityToDTO((NotificationSchedule) notificationRepository.save(notificationSchedule));
        } else
            throw new WrongNotificationClassException("Found a notification of a different type with notification id " + notificationSchedule.getId());
    }

    @Override
    public NotificationTimeoutDTO updateNotificationTimeout(NotificationTimeoutDTO notif) {
        var notificationTimeout = notificationRepository.findById(notif.getId())
                .orElseThrow(new NotFoundException("Notification with notification id " + notif.getId() + " not found"));
        if (notificationTimeout instanceof NotificationTimeout) {
            notificationMapper.updateEntity((NotificationTimeout) notificationTimeout, notif);
            return notificationMapper.entityToDTO((NotificationTimeout) notificationRepository.save(notificationTimeout));
        } else
            throw new WrongNotificationClassException("Found a notification of a different type with notification id " + notificationTimeout.getId());
    }

    @Override
    public List<NotificationDTO> showNotification(User user) {
        Set<Group> groupSet;
        if (user.getGroups() == null) {
            groupSet = new HashSet<>();
        } else groupSet = user.getGroups();
        if (user.getOwnedGroups() != null)
            groupSet.addAll(user.getOwnedGroups());
        List<Notification> notificationList = new ArrayList<>();
        groupSet.forEach(g -> notificationList.addAll(g.getNotificationList()));
        List<NotificationDTO> resultNotificationList = new ArrayList<>();
        for (var notification : notificationList) {
            if (notification instanceof NotificationTimeout) {
                var notif = (NotificationTimeout) notification;
                var pet = notif.getPet();
                var fn = feedNoteRepository.findFirstByPetIdOrderByDateTimeDesc(pet.getId());
                if (fn != null) {
                    LocalDateTime alarmTime = fn.getDateTime().plusSeconds((notif.getElapsed()));
                    boolean notTimeToSend = false;
                    if (notif.getTimes() != null)
                        for (var period : notif.getTimes()) {
                            if (alarmTime.isAfter(ChronoLocalDateTime.from(period.getTimeFrom())) &&
                                    alarmTime.isBefore(ChronoLocalDateTime.from(period.getTimeTo()))) {
                                notTimeToSend = true;
                            }
                        }
                    if (!notTimeToSend && alarmTime.isBefore(ChronoLocalDateTime.from(LocalDateTime.now()))) {
                        resultNotificationList.add(notificationMapper.entityToDTO(notif));
                    }
                }
            } else if (notification instanceof NotificationSchedule) {
                var notif = (NotificationSchedule) notification;
                var times = notif.getTimes();
                var notificationNote = notificationNoteRepository.findByNotificationIdAndUser(notif.getId(), user);
                LocalDateTime lastTime;
                if (notificationNote.isEmpty()) {
                    lastTime = LocalDateTime.MIN;
                } else lastTime = notificationNote.get().getLastTime();
                for (var time : times) {
                    if (time.getNotifTime().isBefore(LocalTime.now()) && time.getNotifTime().isAfter(LocalTime.from(lastTime))) {
                        resultNotificationList.add(notificationMapper.entityToDTO(notif));
                    }
                }
            }
        }
        return resultNotificationList;
    }

    @Override
    public StatusDTO deleteNotification(long notifId) {
        var notification = notificationRepository.findById(notifId)
                .orElseThrow(new NotFoundException("Notification with notification id " + notifId + " not found"));
        var group = notification.getGroup();
        group.getNotificationList().remove(notification);
        var pet = petRepository.findById(notification.getId()).get();
        pet.getNotifications().remove(notification);
        notificationRepository.delete(notification);
        return StatusDTO.builder()
                .status(HttpStatus.OK)
                .description(NOTIFICATION_DELETED)
                .build();
    }

    @Override
    public StatusDTO setTimeInNotificationNote(User user, List<Long> notificationsId) {
        for (var notifId : notificationsId) {
            var notifNote = notificationNoteRepository.findByNotificationIdAndUser(notifId, user)
                    .orElse(NotificationNote.builder()
                            .key(new NotificationNote.Key(user.getId(), notifId))
                            .user(user)
                            .notification(notificationRepository.findById(notifId)
                                    .orElseThrow(new NotFoundException("Notification with notification id " + notifId + " not found")))
                            .build());
            notifNote.setLastTime(LocalDateTime.now());
            notificationNoteRepository.save(notifNote);
        }
        return StatusDTO.builder()
                .status(HttpStatus.OK)
                .description(NOTIFICATION_NOTE_UPDATED)
                .build();
    }
}
