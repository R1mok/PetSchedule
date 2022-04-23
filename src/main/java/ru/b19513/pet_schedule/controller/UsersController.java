package ru.b19513.pet_schedule.controller;

import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.InvitationDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.controller.entity.UserDTO;
import ru.b19513.pet_schedule.repository.entity.User;
import ru.b19513.pet_schedule.service.UserService;

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
    public ResponseEntity<UserDTO> register(@RequestParam String login, @RequestParam String pass,
            @RequestParam String name) {
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
    @GetMapping("/invitations")
    public ResponseEntity<Collection<InvitationDTO>> getInvitations(Principal principal) {
        Collection<InvitationDTO> invitationDTOCollection = userService.getInvitation(principal.getName());
        return new ResponseEntity<>(invitationDTOCollection, HttpStatus.OK);
    }

    @ApiOperation(value = "Принять приглашение")
    @PutMapping("/")
    public ResponseEntity<GroupDTO> acceptInvitation(Principal principal, @RequestParam long groupId) {
        GroupDTO groupDTO = userService.acceptInvintation(principal.getName(), groupId);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Проверить свободность логина")
    @GetMapping("/checkLogin/{login}")
    public ResponseEntity<StatusDTO> isLoginFree(@PathVariable String login) {
        StatusDTO loginFree = userService.isLoginFree(login);
        return new ResponseEntity<>(loginFree, HttpStatus.OK);
    }

    @ApiOperation(value = "Получение данных пользователя по id")
    @GetMapping("/")
    public ResponseEntity<UserDTO> getUser(Authentication principal) {
        UserDTO userDTO = userService.getUserByLogin(principal.getName());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
