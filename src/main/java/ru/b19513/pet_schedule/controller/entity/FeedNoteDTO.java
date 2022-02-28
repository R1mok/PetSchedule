package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.b19513.pet_schedule.repository.entity.Pet;
import ru.b19513.pet_schedule.repository.entity.User;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Запись о кормлении")
public class FeedNoteDTO implements Serializable {
    @Schema(description = "Питомец")
    private final Pet pet;
    @Schema(description = "Пользователь, который оставил запись")
    private final User user;
    @Schema(description = "Время публикации записи")
    private final LocalDateTime dateTime;
    @Schema(description = "Дополнительная информация от пользователя")
    private final String comment;
}
