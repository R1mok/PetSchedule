package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.b19513.pet_schedule.controller.entity.enums.Gender;
import ru.b19513.pet_schedule.controller.entity.enums.PetType;

import java.io.Serializable;

@Data
@Builder
@Schema(description = "Питомец")
public class PetDTO implements Serializable {
    @Schema(description = "ID питомца")
    private final long id;
    @Schema(description = "Имя питомца")
    private final String name;
    @Schema(description = "Описание питомца")
    private final String description;
    @Schema(description = "Вид питомца")
    private final PetType type;
    @Schema(description = "Пол питомца")
    private final Gender gender;
}
