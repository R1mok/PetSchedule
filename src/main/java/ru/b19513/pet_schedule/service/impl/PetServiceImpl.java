package ru.b19513.pet_schedule.service.impl;

import ru.b19513.pet_schedule.controller.entity.FeedNoteDTO;
import ru.b19513.pet_schedule.controller.entity.PetDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.controller.entity.enums.Gender;
import ru.b19513.pet_schedule.controller.entity.enums.PetType;
import ru.b19513.pet_schedule.service.PetService;

import java.time.LocalDateTime;
import java.util.Collection;

public class PetServiceImpl implements PetService {
    @Override
    public PetDTO createPet(long groupId, String name, String description, Gender gender, PetType petType) {
        return null;
    }

    @Override
    public PetDTO updatePet(PetDTO pet) {
        return null;
    }

    @Override
    public Collection<PetDTO> getPets(long groupId) {
        return null;
    }

    @Override
    public StatusDTO deletePet(long petId) {
        return null;
    }

    @Override
    public FeedNoteDTO createFeedNote(long petId, long userId, String comment) {
        return null;
    }

    @Override
    public Collection<FeedNoteDTO> getFeedNotes(long petId) {
        return null;
    }

    @Override
    public Collection<FeedNoteDTO> findFeedNotesByDate(long petId, LocalDateTime from, LocalDateTime to) {
        return null;
    }
}
