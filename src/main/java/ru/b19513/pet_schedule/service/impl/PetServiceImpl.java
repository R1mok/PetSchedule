package ru.b19513.pet_schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.b19513.pet_schedule.controller.entity.FeedNoteDTO;
import ru.b19513.pet_schedule.controller.entity.PetDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.controller.entity.enums.Gender;
import ru.b19513.pet_schedule.controller.entity.enums.PetType;
import ru.b19513.pet_schedule.exceptions.NotFoundException;
import ru.b19513.pet_schedule.repository.GroupRepository;
import ru.b19513.pet_schedule.repository.PetRepository;
import ru.b19513.pet_schedule.repository.entity.Pet;
import ru.b19513.pet_schedule.service.PetService;
import ru.b19513.pet_schedule.service.mapper.EnumMapper;
import ru.b19513.pet_schedule.service.mapper.PetMapper;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class PetServiceImpl implements PetService {
    private final PetMapper petMapper;
    private final PetRepository petRepository;
    private final GroupRepository groupRepository;
    private final EnumMapper enumMapper;

    @Autowired
    public PetServiceImpl(PetMapper petMapper, PetRepository petRepository, GroupRepository groupRepository, EnumMapper enumMapper) {
        this.petMapper = petMapper;
        this.petRepository = petRepository;
        this.groupRepository = groupRepository;
        this.enumMapper = enumMapper;
    }

    @Override
    public PetDTO createPet(long groupId, String name, String description, Gender gender, PetType petType) {
        var group = groupRepository.findById(groupId).orElseThrow(NotFoundException::new);
        if (group.getPets().stream().map(p -> p.getName().equals(name)).findAny().isPresent()) {
            // питомец с таким именем уже существует => нужно либо выкинуть исключение, либо другой вариант, пока не придумал
        }
        var pet = Pet.builder()
                .name(name)
                .group(group)
                .type(enumMapper.DTOtoEntity(petType))
                .gender(enumMapper.DTOtoEntity(gender)) // не добавлен description
                .build();
        return petMapper.entityToDTO(petRepository.save(pet));
    }

    @Override
    public PetDTO updatePet(PetDTO petDTO) {
        var pet = petRepository.findById(petDTO.getId()).orElseThrow(NotFoundException::new);
        if (petDTO.getGender() != null) {
            pet.setGender(enumMapper.DTOtoEntity(petDTO.getGender()));
        }
        if (petDTO.getName() != null) {
            pet.setName(petDTO.getName());
        }
        if (petDTO.getType() != null){
            pet.setType(enumMapper.DTOtoEntity(petDTO.getType()));
        } // не поменял description
        return petMapper.entityToDTO(petRepository.save(pet));
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
