package ru.b19513.pet_schedule.repository.entity.enums;

import lombok.Getter;

@Getter
public enum PetType {
    CAT("Кот", "у него лапки"),
    DOG("Собака", "генерал Гавс");
    final private String name;
    final private String description;

    PetType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
