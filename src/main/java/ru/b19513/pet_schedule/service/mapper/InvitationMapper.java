package ru.b19513.pet_schedule.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.InvitationDTO;
import ru.b19513.pet_schedule.repository.entity.Invitation;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InvitationMapper {

    GroupMapper groupMapper = new GroupMapperImpl();
    default InvitationDTO entityToDTO(Invitation entity) {
        return InvitationDTO.builder()
                .userId(entity.getUser().getId())
                .group(groupMapper.entityToDTO(entity.getGroup()))
                .build();
    }

    List<InvitationDTO> entityToDTO(Collection<Invitation> entities);
}
