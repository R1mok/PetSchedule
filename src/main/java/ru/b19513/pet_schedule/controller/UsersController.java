package ru.b19513.pet_schedule.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.InvitationDTO;
import ru.b19513.pet_schedule.controller.entity.UserDTO;
import ru.b19513.pet_schedule.service.UserService;

import java.util.Collection;

import static ru.b19513.pet_schedule.consts.Consts.NOT_IMPLEMENTED;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }
    @ApiOperation(value = "Регистрация нового пользователя (логин, пароль, имя)")
    @PostMapping("/register/{login}{pass}{name}")
    public ResponseEntity<UserDTO> register(@PathVariable String login,@PathVariable String pass,@PathVariable String name) {
        UserDTO userDTO = userService.signInNewUser(login, pass, name);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @ApiOperation(value = "Изменение данных пользователя")
    @PutMapping("/")
    public ResponseEntity<UserDTO> updateUser(@RequestParam UserDTO user) {
        UserDTO userDTO = userService.updateUser(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить список активных приглашений пользователя по id")
    @GetMapping("/{userId}")
    public ResponseEntity<Collection<InvitationDTO>> getInvitationByUserId(@PathVariable long userId) {
        Collection<InvitationDTO> invitationDTOCollection = userService.getInvitationByUserId(userId);
        return new ResponseEntity<>(invitationDTOCollection, HttpStatus.OK);
    }

    @ApiOperation(value = "Принять приглашение")
    @PutMapping("/{userId}{groupId}")
    public ResponseEntity<GroupDTO> acceptInvitation(@PathVariable long userId, @PathVariable long groupId) {
        GroupDTO groupDTO = userService.acceptInvintation(userId, groupId);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Проверить свободность логина")
    @GetMapping("/{login}")
    public ResponseEntity<Boolean> getInvitationByUserId(@PathVariable String login) {
        Boolean loginFree = userService.isLoginFree(login);
        return new ResponseEntity<>(loginFree, HttpStatus.OK);
    }

    @ApiOperation(value = "Получение данных пользователя по id")
    @GetMapping("/{userId}")
    public ResponseEntity<String> getUser(@PathVariable String userId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }
}
