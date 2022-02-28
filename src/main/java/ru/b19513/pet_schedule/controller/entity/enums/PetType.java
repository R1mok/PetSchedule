package ru.b19513.pet_schedule.controller.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "Вид питомца")
public enum PetType {
    CAT("Кот", "у него лапки"),
    DOG("Собака", "генерал Гавс");
    @Schema(description = "Название вида питомца")
    final private String name;
    @Schema(description = "Описание вида питомца")
    final private String description;
}


