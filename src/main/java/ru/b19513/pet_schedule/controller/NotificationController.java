package ru.b19513.pet_schedule.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ru.b19513.pet_schedule.consts.Consts.NOT_IMPLEMENTED;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @ApiOperation(value = "Получить список уведомлений группы, наступивших после указанной даты")
    @GetMapping("/new")
    public ResponseEntity<String> getNewNotifications(@RequestParam String groupId, @RequestParam String last) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Получить список объектов напоминаний заданной группы")
    @GetMapping("/")
    public ResponseEntity<String> getAllNotifications(@RequestParam String groupId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Добавить новое напоминание")
    @PostMapping("/")
    public ResponseEntity<String> postNotification() {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Изменить напоминание по id")
    @PutMapping("/{notifId}")
    public ResponseEntity<String> updateNotification(@PathVariable String notifId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Удалить напоминание по id")
    @DeleteMapping("/{notifId}")
    public ResponseEntity<String> deleteNotification(@PathVariable String notifId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }
}
