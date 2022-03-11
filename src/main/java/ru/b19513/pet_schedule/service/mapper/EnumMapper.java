package ru.b19513.pet_schedule.service.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnumMapper {
    ru.b19513.pet_schedule.controller.entity.enums.Gender entityToDTO(ru.b19513.pet_schedule.repository.entity.enums.Gender gender);

    ru.b19513.pet_schedule.repository.entity.enums.Gender DTOtoEntity(ru.b19513.pet_schedule.controller.entity.enums.Gender gender);


    ru.b19513.pet_schedule.controller.entity.enums.PetType entityToDTO(ru.b19513.pet_schedule.repository.entity.enums.PetType petType);

    ru.b19513.pet_schedule.repository.entity.enums.PetType DTOtoEntity(ru.b19513.pet_schedule.controller.entity.enums.PetType petType);
}
