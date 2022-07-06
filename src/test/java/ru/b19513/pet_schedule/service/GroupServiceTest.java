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
@Transactional
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
        var user = userRepository.findAll().stream().findAny().get();
        groupService.createGroup(u1, "g1");
        var group = groupRepository.findAll().stream().findAny().get();
        Assertions.assertEquals(g1.getName(), group.getName());
        Assertions.assertEquals(u1.getName(), group.getOwner().getName());
        Assertions.assertTrue(groupRepository.findById(group.getId()).get().getUsers().contains(u1));
        userRepository.delete(user);
        groupRepository.delete(group);
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
        var user1 = userRepository.findAll().stream().findAny().get();
        groupService.createGroup(u1, "g1");
        var group = groupRepository.findAll().stream().findFirst().get();
        GroupDTO groupDTO = GroupDTO.builder()
                .id(group.getId())
                .name("changedD1")
                .description("changedDesciption")
                .build();
        groupService.updateGroup(u1, groupDTO);
        Assertions.assertEquals(groupDTO.getName(), groupRepository.findById(group.getId()).get().getName());
        Assertions.assertEquals(groupDTO.getDescription(), groupRepository.findById(group.getId()).get().getDescription());
        userRepository.delete(u1);
        groupRepository.delete(group);
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
        var listUsers = userRepository.findAll();
        var user1 = listUsers.get(0);
        var user2 = listUsers.get(1);
        groupService.createGroup(u1, "g1");
        var group = groupRepository.findAll().stream().findAny().get();
        groupService.inviteUser(u1, group.getId(), user2.getId());
        userService.acceptInvintation(u2, group.getId());
        Assertions.assertEquals(1, userRepository.findById(user2.getId()).get().getGroups().size());
        Assertions.assertEquals(2, groupRepository.findById(group.getId()).get().getUsers().size());
        Assertions.assertEquals(Set.of(u1, u2), groupRepository.findById(group.getId()).get().getUsers());
        groupRepository.delete(group);
        userRepository.delete(user1);
        userRepository.delete(user2);
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
        var listUsers = userRepository.findAll();
        var user1 = listUsers.get(0);
        var user2 = listUsers.get(1);
        groupService.createGroup(u1, "g1");
        var group = groupRepository.findAll().stream().findAny().get();
        groupService.inviteUser(u1, group.getId(), user2.getId());
        userService.acceptInvintation(u2, group.getId());
        Assertions.assertEquals(2, groupRepository.findById(group.getId()).get().getUsers().size());
        Assertions.assertEquals(Set.of(u1, u2), groupRepository.findById(group.getId()).get().getUsers());
        groupService.kickUser(u1, group.getId(), user2.getId());
        Assertions.assertEquals(1, groupRepository.findById(group.getId()).get().getUsers().size());
        Assertions.assertEquals(Set.of(u1), groupRepository.findById(group.getId()).get().getUsers());
        userRepository.delete(u1);
        userRepository.delete(u2);
        groupRepository.delete(groupRepository.findById(group.getId()).get());
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
        userRepository.delete(u1);
    }
}