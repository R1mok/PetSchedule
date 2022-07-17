package ru.b19513.pet_schedule.controller.entity;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String login;
    private String password;
}
