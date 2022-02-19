package ru.b19513.pet_schedule.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import static ru.b19513.pet_schedule.consts.Consts.NOT_IMPLEMENTED;

@Controller("users/")
@EnableAutoConfiguration
public class UsersController {

    @PostMapping("register/")
    public String register() {
        return NOT_IMPLEMENTED;
    }
}
