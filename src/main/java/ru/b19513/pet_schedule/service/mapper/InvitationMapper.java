package ru.b19513.pet_schedule.service.mapper;

import org.mapstruct.Mapper;
import ru.b19513.pet_schedule.controller.entity.InvitationDTO;
import ru.b19513.pet_schedule.repository.entity.Invitation;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InvitationMapper {
    InvitationDTO entityToDTO(Invitation entity);

    List<InvitationDTO> entityToDTO(Collection<Invitation> entities);
}
