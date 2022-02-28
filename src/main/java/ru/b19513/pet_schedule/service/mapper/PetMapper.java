package ru.b19513.pet_schedule.service.mapper;

import org.mapstruct.Mapper;
import ru.b19513.pet_schedule.controller.entity.PetDTO;
import ru.b19513.pet_schedule.repository.entity.Pet;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {
    PetDTO entityToDTO(Pet entity);

    List<PetDTO> entityToDTO(Collection<Pet> entities);
}
