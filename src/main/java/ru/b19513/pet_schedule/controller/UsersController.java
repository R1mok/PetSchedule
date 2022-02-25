package ru.b19513.pet_schedule.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ru.b19513.pet_schedule.consts.Consts.NOT_IMPLEMENTED;

@RestController
@RequestMapping("/users")
public class UsersController {

    @ApiOperation(value = "Регистрация нового пользователя")
    @PostMapping("/register")
    public ResponseEntity<String> register() {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Изменение данных пользователя по id")
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Получение данных пользователя по id")
    @GetMapping("/{userId}")
    public ResponseEntity<String> getUser(@PathVariable String userId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }
}
