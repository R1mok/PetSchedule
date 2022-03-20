package ru.b19513.pet_schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.b19513.pet_schedule.controller.entity.FeedNoteDTO;
import ru.b19513.pet_schedule.controller.entity.PetDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.controller.entity.enums.Gender;
import ru.b19513.pet_schedule.controller.entity.enums.PetType;
import ru.b19513.pet_schedule.exceptions.NameTakenException;
import ru.b19513.pet_schedule.exceptions.NotFoundException;
import ru.b19513.pet_schedule.repository.FeedNoteRepository;
import ru.b19513.pet_schedule.repository.GroupRepository;
import ru.b19513.pet_schedule.repository.PetRepository;
import ru.b19513.pet_schedule.repository.UserRepository;
import ru.b19513.pet_schedule.repository.entity.FeedNote;
import ru.b19513.pet_schedule.repository.entity.Pet;
import ru.b19513.pet_schedule.service.PetService;
import ru.b19513.pet_schedule.service.mapper.EnumMapper;
import ru.b19513.pet_schedule.service.mapper.FeedNoteMapper;
import ru.b19513.pet_schedule.service.mapper.PetMapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.b19513.pet_schedule.consts.Consts.PET_DELETED;

@Service
public class PetServiceImpl implements PetService {
    private final PetMapper petMapper;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final EnumMapper enumMapper;
    private final FeedNoteMapper feedNoteMapper;
    private final FeedNoteRepository feedNoteRepository;

    @Autowired
    public PetServiceImpl(PetMapper petMapper, PetRepository petRepository, UserRepository userRepository, GroupRepository groupRepository, EnumMapper enumMapper, FeedNoteMapper feedNoteMapper, FeedNoteRepository feedNoteRepository) {
        this.petMapper = petMapper;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.enumMapper = enumMapper;
        this.feedNoteMapper = feedNoteMapper;
        this.feedNoteRepository = feedNoteRepository;
    }

    @Override
    public PetDTO createPet(long groupId, String name, String description, Gender gender, PetType petType) {
        var group = groupRepository.findById(groupId).orElseThrow(NotFoundException::new);
        if (group.getPets().stream().map(p -> p.getName().equals(name)).findAny().isPresent()) {
            throw new NameTakenException();
        }
        var pet = Pet.builder()
                .name(name)
                .group(group)
                .type(enumMapper.DTOtoEntity(petType))
                .gender(enumMapper.DTOtoEntity(gender))
                .description(description)
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
        }
        if (petDTO.getDescription() != null){
            pet.setDescription(petDTO.getDescription());
        }
        return petMapper.entityToDTO(petRepository.save(pet));
    }

    @Override
    public Collection<PetDTO> getPets(long groupId) {
        var group = groupRepository.findById(groupId).orElseThrow(NotFoundException::new);
        return petMapper.entityToDTO(group.getPets());
    }

    @Override
    public StatusDTO deletePet(long petId) {
        var pet = petRepository.findById(petId).orElseThrow(NotFoundException::new);
        petRepository.delete(pet);
        return StatusDTO.builder()
                .status(HttpStatus.OK)
                .description(PET_DELETED)
                .build();
    }

    @Override
    public FeedNoteDTO createFeedNote(long petId, long userId, String comment) {
        var pet = petRepository.findById(petId).orElseThrow(NotFoundException::new);
        var user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        var newFeedNote = new FeedNote();
        newFeedNote.setPet(pet);
        newFeedNote.setDateTime(LocalDateTime.now());
        newFeedNote.setUser(user);
        return feedNoteMapper.entityToDTO(newFeedNote);

    }

    @Override
    public Collection<FeedNoteDTO> getFeedNotes(long petId) {
        var pet = petRepository.findById(petId).orElseThrow(NotFoundException::new);
        var collectionOfFeedNotes = feedNoteRepository.findByPet(pet);
        return feedNoteMapper.entityToDTO(collectionOfFeedNotes);
    }

    @Override
    public Collection<FeedNoteDTO> findFeedNotesByDate(long petId, LocalDateTime from, LocalDateTime to) {
        var pet = petRepository.findById(petId).orElseThrow(NotFoundException::new);
        var collectionOfFeedNotes = feedNoteRepository.findByPetAndDateTimeIsBetween(pet, from, to);
        return feedNoteMapper.entityToDTO(collectionOfFeedNotes);
    }
}
