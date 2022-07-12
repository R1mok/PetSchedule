package ru.b19513.pet_schedule.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.b19513.pet_schedule.controller.entity.InvitationDTO;
import ru.b19513.pet_schedule.controller.entity.UserDTO;
import ru.b19513.pet_schedule.repository.GroupRepository;
import ru.b19513.pet_schedule.repository.InvitationRepository;
import ru.b19513.pet_schedule.repository.UserRepository;
import ru.b19513.pet_schedule.repository.entity.User;
import ru.b19513.pet_schedule.service.GroupService;
import ru.b19513.pet_schedule.service.UserService;
import ru.b19513.pet_schedule.service.mapper.GroupMapper;
import ru.b19513.pet_schedule.service.mapper.UserMapper;

import javax.transaction.Transactional;
import java.util.Set;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    InvitationRepository invitationRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GroupMapper groupMapper;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Test
    void signInNewUser() {
        userService.signInNewUser("R1mok", "pass", "Anton");
        var userInRepo = userRepository.findAll().stream().findAny().get();
        Assertions.assertEquals("R1mok", userInRepo.getLogin());
        Assertions.assertEquals("Anton", userInRepo.getName());
        Assertions.assertTrue(bCryptPasswordEncoder.matches("pass",
                userInRepo.getPassword()));
        userRepository.deleteAll();
    }

    @Test
    void updateUser() {
        UserDTO userDTO = UserDTO.builder().name("Anton").build();
        userService.signInNewUser("R1mok", "pass", "Onton");
        var userInRepo = userRepository.findAll().stream().findAny().get();
        userService.updateUser(User.builder().name("Onton").id(userInRepo.getId()).login("R1mok").build(), userDTO);
        var user = userRepository.findAll().stream().findAny().get();
        Assertions.assertEquals("Anton", user.getName());
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void getInvitation() {
        userService.signInNewUser("R1mok", "pass", "Anton");
        userService.signInNewUser("HamzReg", "pass", "Regina");
        var userInRepo1 = userRepository.findAll().get(0);
        var userInRepo2 = userRepository.findAll().get(1);
        groupService.createGroup(userInRepo1, "group");
        var group = groupRepository.findAll().get(0);
        groupService.inviteUser(userInRepo1, group.getId(), userInRepo2.getId());
        var oneInvitationOfUser = userService.getInvitation(userInRepo2);
        Assertions.assertEquals(1, oneInvitationOfUser.size());
        Assertions.assertEquals(InvitationDTO.builder()
                .userId(userInRepo2.getId())
                .group(groupMapper.entityToDTO(group)).build(), oneInvitationOfUser.stream().findAny().get());

        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void acceptInvitation() {
        userService.signInNewUser("R1mok", "pass", "Anton");
        var user = userRepository.findAll().get(0);
        userService.signInNewUser("HamzReg", "pass", "Regina");
        var groupOwner = userRepository.findAll().get(1);
        groupService.createGroup(groupOwner, "group");
        var group = groupRepository.findAll().get(0);
        groupService.inviteUser(groupOwner, group.getId(), user.getId());
        userService.acceptInvitation(user, group.getId());
        Assertions.assertEquals(Set.of(groupOwner, user), groupRepository.findAll().get(0).getUsers());
        userRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    void isLoginFree() {
        Assertions.assertEquals("true", userService.isLoginFree("R1mok").getDescription());
        userService.signInNewUser("R1mok", "pass", "Anton");
        Assertions.assertEquals("false", userService.isLoginFree("R1mok").getDescription());
        userRepository.deleteAll();
    }

    @Test
    void findUsersByLogin() {
        Assertions.assertTrue(userService.findUsersByLogin("R1mok").stream().findAny().isEmpty());
        userService.signInNewUser("R1mok", "pass", "Anton");
        var login1 = userService.findUsersByLogin("R1mok").get(0).getLogin();
        Assertions.assertEquals("R1mok", login1);
        userRepository.deleteAll();
    }

    @Test
    void getUser() {
        var userDTO = userService.signInNewUser("R1mok", "pass", "Anton");
        var user = User.builder()
                .name("Anton")
                .id(userDTO.getId())
                .login("R1mok")
                .build();
        var userFromMethod = userService.getUser(user);
        var expected = userMapper.entityToDTO(userRepository.findAll().get(0));
        Assertions.assertEquals(expected.getLogin(),
                userFromMethod.getLogin());
        Assertions.assertEquals(expected.getName(), userFromMethod.getName());
        Assertions.assertEquals(expected.getId(), userFromMethod.getId());
        userRepository.deleteAll();
    }
}