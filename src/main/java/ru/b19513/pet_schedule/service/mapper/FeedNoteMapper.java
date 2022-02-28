package ru.b19513.pet_schedule.service.mapper;

import org.mapstruct.Mapper;
import ru.b19513.pet_schedule.controller.entity.FeedNoteDTO;
import ru.b19513.pet_schedule.repository.entity.FeedNote;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedNoteMapper {
    FeedNoteDTO entityToDTO(FeedNote entity);

    List<FeedNoteDTO> entityToDTO(Collection<FeedNote> entities);
}
