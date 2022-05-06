package ru.b19513.pet_schedule.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.b19513.pet_schedule.controller.entity.NotificationDTO;
import ru.b19513.pet_schedule.controller.entity.NotificationScheduleDTO;
import ru.b19513.pet_schedule.controller.entity.NotificationTimeoutDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.repository.entity.User;
import ru.b19513.pet_schedule.service.NotificationService;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @ApiOperation(value = "Добавить новое напоминание типа Timeout.")
    @PostMapping("/timeout/")
    public ResponseEntity<NotificationTimeoutDTO> postNotificationTimeout(@RequestParam long groupId, @RequestParam String comment,
                                                          @RequestParam long petId, @RequestParam int elapsed) {
        var notificationDTO = notificationService.createNotificationTimeout(groupId, petId, comment, elapsed);
        return ResponseEntity.ok(notificationDTO);
    }

    @ApiOperation(value = "Добавить новое напоминание типа Schedule.")
    @PostMapping("/schedule/")
    public ResponseEntity<NotificationScheduleDTO> postNotificationSchedule(@RequestParam long groupId, @RequestParam String comment,
                                                                            @RequestParam long petId, @RequestParam List<LocalTime> times) {
        var notificationDTO = notificationService.createNotificationSchedule(groupId, petId, comment, times);
        return ResponseEntity.ok(notificationDTO);
    }

    @ApiOperation(value = "Изменить напоминание.")
    @PutMapping("/")
    public ResponseEntity<NotificationDTO> updateNotification(@RequestParam NotificationDTO notif){
        NotificationDTO notificationDTO;
        if (notif instanceof NotificationTimeoutDTO){
            notificationDTO = notificationService.updateNotificationTimeout((NotificationTimeoutDTO) notif);
        } else {
            notificationDTO = notificationService.updateNotificationSchedule((NotificationScheduleDTO) notif);
        }
        return ResponseEntity.ok(notificationDTO);
    }

    @ApiOperation(value = "Показать напоминания.")
    @GetMapping("/")
    public ResponseEntity<List<NotificationDTO>> showNotification(Authentication auth){
        var user = ((User)auth.getDetails());
        var notificationList = notificationService.showNotification(user);
        return ResponseEntity.ok(notificationList);
    }

    @ApiOperation(value = "Удалить напоминание по id.")
    @DeleteMapping("/{notifId}")
    public ResponseEntity<StatusDTO> deleteNotification(@PathVariable long notifId) {
        var status = notificationService.deleteNotification(notifId);
        return ResponseEntity.ok(status);
    }

    @ApiOperation(value = "Проставить текущее время в напоминаниях, которые уже были показаны.")
    @PatchMapping("/setTime/{userId}")
    public ResponseEntity<StatusDTO> setTimeInNotificationNote(Authentication auth, @RequestParam List<Long> notificationsId){
        var user = ((User)auth.getDetails());
        var status = notificationService.setTimeInNotificationNote(user, notificationsId);
        return ResponseEntity.ok(status);
    }
}
