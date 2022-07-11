package ru.b19513.pet_schedule.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.b19513.pet_schedule.controller.entity.enums.Gender;
import ru.b19513.pet_schedule.controller.entity.enums.PetType;
import ru.b19513.pet_schedule.repository.GroupRepository;
import ru.b19513.pet_schedule.repository.NotificationRepository;
import ru.b19513.pet_schedule.repository.PetRepository;
import ru.b19513.pet_schedule.repository.UserRepository;
import ru.b19513.pet_schedule.repository.entity.NotificationSchedule;
import ru.b19513.pet_schedule.repository.entity.NotificationTimeout;
import ru.b19513.pet_schedule.repository.entity.User;
import ru.b19513.pet_schedule.service.GroupService;
import ru.b19513.pet_schedule.service.NotificationService;
import ru.b19513.pet_schedule.service.PetService;
import ru.b19513.pet_schedule.service.UserService;
import ru.b19513.pet_schedule.service.mapper.NotificationMapper;

import javax.transaction.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationServiceImplTest {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    UserService userService;
    @Autowired
    PetService petService;
    @Autowired
    GroupService groupService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationMapper notificationMapper;

    @Test
    @Transactional
    void createNotificationTimeout() {
        var userOwnerDTO = userService.signInNewUser("R1mok", "pass", "Anton");
        var groupDTO = groupService.createGroup(userRepository.getById(userOwnerDTO.getId()), "group");
        var petDTO = petService.createPet(groupDTO.getId(), "Barsik", "My lover", Gender.MALE, PetType.CAT);
        var notifTimeoutDTO = notificationService.createNotificationTimeout(groupDTO.getId(),
                petDTO.getId(), "Barsik should to feed", 5);
        var notifInRepo = notificationMapper.entityToDTO((NotificationTimeout) notificationRepository.findAll().get(0));
        Assertions.assertEquals(notifInRepo.getId(), notifTimeoutDTO.getId());
        Assertions.assertEquals(notifInRepo.getGroupId(), notifTimeoutDTO.getGroupId());
        Assertions.assertEquals(notifInRepo.getComment(), notifTimeoutDTO.getComment());
        Assertions.assertEquals(notifInRepo.getPeriods(), notifTimeoutDTO.getPeriods());
        petRepository.deleteAll();
        notificationRepository.deleteAll();
        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void createNotificationSchedule() {
        var userOwnerDTO = userService.signInNewUser("R1mok", "pass", "Anton");
        var groupDTO = groupService.createGroup(userRepository.getById(userOwnerDTO.getId()), "group");
        var petDTO = petService.createPet(groupDTO.getId(), "Barsik", "My lover", Gender.MALE, PetType.CAT);
        var notifScheduleDTO = notificationService.createNotificationSchedule(groupDTO.getId(),
                petDTO.getId(), "Barsik should to feed", List.of(LocalTime.of(12, 30)));
        var notifInRepo = notificationMapper.entityToDTO((NotificationSchedule) notificationRepository.findAll().get(0));
        Assertions.assertEquals(notifInRepo.getId(), notifScheduleDTO.getId());
        Assertions.assertEquals(notifInRepo.getGroupId(), notifScheduleDTO.getGroupId());
        Assertions.assertEquals(notifInRepo.getComment(), notifScheduleDTO.getComment());
        Assertions.assertEquals(notifInRepo.getTimes(), notifScheduleDTO.getTimes());
        petRepository.deleteAll();
        notificationRepository.deleteAll();
        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void updateNotificationSchedule() {
    }

    @Test
    void updateNotificationTimeout() {
    }

    @Test
    @Transactional
    void showNotification() throws InterruptedException {
        var userDTO = userService.signInNewUser("R1mok", "pass", "Anton");
        var groupDTO = groupService.createGroup(userRepository.getById(userDTO.getId()), "group");
        var group = groupRepository.findAll().get(0);
        var petDTO = petService.createPet(groupDTO.getId(), "Barsik", "My lover", Gender.MALE, PetType.CAT);
        var fnDTO = petService.createFeedNote(petDTO.getId(), userDTO.getId(), "Feed Barsik");
        notificationService.createNotificationTimeout(groupDTO.getId(), petDTO.getId(), "Feed Barsik", 1);
        Thread.sleep(1000);
        var notifActualDTO = notificationService.showNotification(userRepository.findByLogin(userDTO.getLogin()).get()).get(0);
        var notifExpectedDTO = notificationMapper.entityToDTO(notificationRepository
                .findAll().stream().filter(e -> e.getGroup().equals(group)).collect(Collectors.toSet())).get(0);
        Assertions.assertEquals(notifExpectedDTO.getComment(), notifActualDTO.getComment());
        Assertions.assertEquals(notifExpectedDTO.getId(), notifActualDTO.getId());
        Assertions.assertEquals(notifExpectedDTO.getGroupId(), notifActualDTO.getGroupId());
        notificationRepository.deleteAll();
        petRepository.deleteAll();
        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void deleteNotification() {
        var userDTO = userService.signInNewUser("R1mok", "pass", "Anton");
        var groupDTO = groupService.createGroup(userRepository.getById(userDTO.getId()), "group");
        var group = groupRepository.findAll().get(0);
        var petDTO = petService.createPet(groupDTO.getId(), "Barsik", "My lover", Gender.MALE, PetType.CAT);
        var fnDTO = petService.createFeedNote(petDTO.getId(), userDTO.getId(), "Feed Barsik");
        var notifDTO = notificationService.createNotificationTimeout(groupDTO.getId(), petDTO.getId(), "Feed Barsik", 1);
        notificationService.deleteNotification(notifDTO.getId());
        var user = userRepository.findByLogin(userDTO.getLogin()).get();
        var countOfNotification = notificationService.showNotification(user).size();
        Assertions.assertEquals(0, countOfNotification);
        notificationRepository.deleteAll();
        petRepository.deleteAll();
        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void setTimeInNotificationNote() {
    }
}