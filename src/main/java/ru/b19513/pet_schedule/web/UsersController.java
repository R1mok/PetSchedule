package ru.b19513.pet_schedule.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ru.b19513.pet_schedule.consts.Consts.NOT_IMPLEMENTED;

@RestController
@RequestMapping("/users")
public class UsersController {

    @PostMapping("/register")
    public ResponseEntity<String> register() {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }
}
