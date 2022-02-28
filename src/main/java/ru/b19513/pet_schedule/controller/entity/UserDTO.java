package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.b19513.pet_schedule.controller.entity.enums.Gender;
import ru.b19513.pet_schedule.repository.entity.Invitation;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@Schema(description = "Класс пользователя")
public class UserDTO implements Serializable {
    @Schema(description = "ID пользователя")
    private final long id;
    @Schema(description = "Логин пользователя")
    private final String login;
    @Schema(description = "Имя пользователя")
    private final String name;
    @Schema(description = "Описание пользователя")
    private final String about;
    @Schema(description = "Пол пользователя")
    private final Gender gender;
    @Schema(description = "Приглашения пользователя в группы")
    private final List<Invitation> invitations;
}
