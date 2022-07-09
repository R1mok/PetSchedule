package ru.b19513.pet_schedule.service;

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
import ru.b19513.pet_schedule.service.mapper.GroupMapper;
import ru.b19513.pet_schedule.service.mapper.UserMapper;

import javax.transaction.Transactional;

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
                .user(userMapper.entityToDTO(userInRepo2))
                .group(groupMapper.entityToDTO(group)).build(), oneInvitationOfUser.stream().findAny().get());

        userRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    void acceptInvintation() {
    }

    @Test
    void isLoginFree() {
    }

    @Test
    void findUsersByLogin() {
    }

    @Test
    void getUser() {
    }
}