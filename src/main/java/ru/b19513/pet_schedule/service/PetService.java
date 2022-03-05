package ru.b19513.pet_schedule.service;

import ru.b19513.pet_schedule.controller.entity.FeedNoteDTO;
import ru.b19513.pet_schedule.controller.entity.PetDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.controller.entity.enums.Gender;
import ru.b19513.pet_schedule.controller.entity.enums.PetType;

import java.time.LocalDateTime;
import java.util.Collection;

public interface PetService {
    PetDTO createPet(long groupId, String name, String description, Gender gender, PetType petType);

    PetDTO updatePet(PetDTO pet);

    Collection<PetDTO> getPets(long groupId);

    StatusDTO deletePet(long petId);

    FeedNoteDTO createFeedNote(long petId, long userId, String comment);

    Collection<FeedNoteDTO> getFeedNotes(long petId);

    Collection<FeedNoteDTO> findFeedNotesByDate(long petId, LocalDateTime from, LocalDateTime to);
}
