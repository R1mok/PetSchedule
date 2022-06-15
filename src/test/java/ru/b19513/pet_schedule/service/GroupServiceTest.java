package ru.b19513.pet_schedule.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.b19513.pet_schedule.repository.*;
import ru.b19513.pet_schedule.repository.entity.*;
import ru.b19513.pet_schedule.repository.entity.enums.Gender;
import lombok.val;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
class GroupServiceTest {

    @Autowired
    GroupService groupService;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    void createGroup() {
        val u1 = User.builder()
                .login("R1mok")
                .name("Anton")
                .gender(Gender.MALE)
                .password("sdasd")
                .build();
        val g1 = Group.builder()
                .name("g1")
                .owner(u1)
                .build();
        userRepository.save(u1);
        groupService.createGroup(u1, "g1");
        var group = groupRepository.findById(1L).get();
        Assertions.assertEquals(g1.getName(), group.getName());
        Assertions.assertEquals(u1.getName(), group.getOwner().getName());
    }

    @Test
    void updateGroup() {
    }

    @Test
    void inviteUser() {

        val u1 = User.builder()
                .login("R1mok")
                .name("Anton")
                .gender(Gender.MALE)
                .password("sdasd")
                .build();
        val u2 = User.builder()
                .login("Jawa")
                .name("Anatoliy")
                .gender(Gender.MALE)
                .password("nvjmfd")
                .build();
        userRepository.save(u1);
        userRepository.save(u2);
        groupService.createGroup(u1, "g1");
        groupService.inviteUser(u1, 1L, 2L);
    }

    @Test
    void kickUser() {
    }

    @Test
    void deleteGroup() {
    }
}