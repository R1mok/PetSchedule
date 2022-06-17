package ru.b19513.pet_schedule.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.b19513.pet_schedule.repository.*;
import ru.b19513.pet_schedule.repository.entity.*;
import ru.b19513.pet_schedule.repository.entity.enums.Gender;
import ru.b19513.pet_schedule.controller.entity.*;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import java.util.Set;
import javax.transaction.Transactional;

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
    @Transactional
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
        Assertions.assertTrue(groupRepository.findById(1L).get().getUsers().contains(u1));
    }

    @Test
    @Transactional
    void updateGroup() {
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
        GroupDTO groupDTO = GroupDTO.builder()
                .id(1L)
                .name("changedD1")
                .description("changedDesciption")
                .build();
        groupService.updateGroup(u1, groupDTO);
        Assertions.assertEquals(groupDTO.getName(), groupRepository.findById(1L).get().getName());
        Assertions.assertEquals(groupDTO.getDescription(), groupRepository.findById(1L).get().getDescription());
    }

    @Test
    @Transactional
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
        userService.acceptInvintation(u2, 1L);
        Assertions.assertEquals(1, userRepository.findById(2L).get().getGroups().size());
        Assertions.assertEquals(2, groupRepository.findById(1L).get().getUsers().size());
        Assertions.assertEquals(Set.of(u1, u2), groupRepository.findById(1L).get().getUsers());
    }

    @Test
    @Transactional
    void kickUser() {
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
        userService.acceptInvintation(u2, 1L);
        Assertions.assertEquals(2, groupRepository.findById(1L).get().getUsers().size());
        Assertions.assertEquals(Set.of(u1, u2), groupRepository.findById(1L).get().getUsers());
        groupService.kickUser(u1, 1L, 2L);
        Assertions.assertEquals(1, groupRepository.findById(1L).get().getUsers().size());
        Assertions.assertEquals(Set.of(u1), groupRepository.findById(1L).get().getUsers());
    }

    @Test
    @Transactional
    void deleteGroup() {
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
        groupService.deleteGroup(1L, u1);
        Assertions.assertFalse(groupRepository.findById(1L).isPresent());
        Assertions.assertNull(userRepository.findById(1L).get().getGroups());
    }
}