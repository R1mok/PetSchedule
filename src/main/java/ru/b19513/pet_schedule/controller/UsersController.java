package ru.b19513.pet_schedule.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.InvitationDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.controller.entity.UserDTO;
import ru.b19513.pet_schedule.repository.entity.User;
import ru.b19513.pet_schedule.service.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users controller", description = "Контроллер пользователей")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Регистрация нового пользователя")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestParam String login, @RequestParam String pass,
                                            @RequestParam String name) {
        UserDTO userDTO = userService.signInNewUser(login, pass, name);
        return ResponseEntity.ok(userDTO);
    }

    @Operation(summary = "Изменение данных пользователя")
    @PatchMapping("/")
    public ResponseEntity<UserDTO> updateUser(Authentication auth, @RequestParam UserDTO user) {
        UserDTO userDTO = userService.updateUser((User) auth.getPrincipal(), user);
        return ResponseEntity.ok(userDTO);
    }

    @Operation(summary = "Получить список активных приглашений пользователя")
    @GetMapping("/invitations")
    public ResponseEntity<Collection<InvitationDTO>> getInvitations(Authentication auth) {
        Collection<InvitationDTO> invitationDTOCollection = userService.getInvitation((User) auth.getPrincipal());
        return ResponseEntity.ok(invitationDTOCollection);
    }

    @Operation(summary = "Принять приглашение")
    @PutMapping("/")
    public ResponseEntity<GroupDTO> acceptInvitation(Authentication auth, @RequestParam long groupId) {
        GroupDTO groupDTO = userService.acceptInvintation((User) auth.getPrincipal(), groupId);
        return ResponseEntity.ok(groupDTO);
    }

    @Operation(summary = "Проверить свободность логина")
    @GetMapping("/checkLogin/{login}")
    public ResponseEntity<StatusDTO> isLoginFree(@PathVariable String login) {
        StatusDTO loginFree = userService.isLoginFree(login);
        return ResponseEntity.ok(loginFree);
    }

    @Operation(summary = "Получение данных пользователя")
    @GetMapping("/")
    public ResponseEntity<UserDTO> getUser(Authentication auth) {
        UserDTO userDTO = userService.getUser((User) auth.getPrincipal());
        return ResponseEntity.ok(userDTO);
    }

    @Operation(summary = "Получение пяти пользователей с логином, начинающимся на переданное слово")
    @GetMapping("/findByLogin")
    public ResponseEntity<List<UserDTO>> getUsersByLogin(@RequestParam String loginBegining) {
        List<UserDTO> userDTO = userService.findUsersByLogin(loginBegining);
        return ResponseEntity.ok(userDTO);
    }
}
