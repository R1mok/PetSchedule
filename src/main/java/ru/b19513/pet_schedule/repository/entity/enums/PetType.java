package ru.b19513.pet_schedule.repository.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetType {
    CAT("Кот", "у него лапки"),
    DOG("Собака", "генерал Гавс");
    final private String name;
    final private String description;
}
