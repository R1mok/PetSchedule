package ru.b19513.pet_schedule.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.InvitationDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.controller.entity.UserDTO;
import ru.b19513.pet_schedule.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }
    @ApiOperation(value = "Регистрация нового пользователя")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestParam String login,@RequestParam String pass,@RequestParam String name) {
        UserDTO userDTO = userService.signInNewUser(login, pass, name);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @ApiOperation(value = "Изменение данных пользователя")
    @PatchMapping("/")
    public ResponseEntity<UserDTO> updateUser(@RequestParam UserDTO user) {
        UserDTO userDTO = userService.updateUser(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить список активных приглашений пользователя по id")
    @GetMapping("/{userId}/invitations")
    public ResponseEntity<Collection<InvitationDTO>> getInvitations(@PathVariable long userId) {
        Collection<InvitationDTO> invitationDTOCollection = userService.getInvitationByUserId(userId);
        return new ResponseEntity<>(invitationDTOCollection, HttpStatus.OK);
    }

    @ApiOperation(value = "Принять приглашение")
    @PutMapping("/")
    public ResponseEntity<GroupDTO> acceptInvitation(@RequestParam long userId, @RequestParam long groupId) {
        GroupDTO groupDTO = userService.acceptInvintation(userId, groupId);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Проверить свободность логина")
    @GetMapping("/checkLogin/{login}")
    public ResponseEntity<StatusDTO> isLoginFree(@PathVariable String login) {
        StatusDTO loginFree = userService.isLoginFree(login);
        return new ResponseEntity<>(loginFree, HttpStatus.OK);
    }

    @ApiOperation(value = "Получение данных пользователя по id")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long userId) {
        UserDTO userDTO = userService.getUser(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
