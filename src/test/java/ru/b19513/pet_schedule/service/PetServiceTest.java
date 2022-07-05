package ru.b19513.pet_schedule.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.b19513.pet_schedule.controller.entity.PetDTO;
import ru.b19513.pet_schedule.controller.entity.enums.Gender;
import ru.b19513.pet_schedule.controller.entity.enums.PetType;
import ru.b19513.pet_schedule.repository.GroupRepository;
import ru.b19513.pet_schedule.repository.PetRepository;
import ru.b19513.pet_schedule.repository.entity.Group;
import ru.b19513.pet_schedule.repository.entity.Pet;
import ru.b19513.pet_schedule.service.*;
import ru.b19513.pet_schedule.service.mapper.PetMapper;;

import javax.transaction.Transactional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PetServiceTest {

    @Autowired
    PetRepository petRepository;

    @Autowired
    PetMapper petMapper;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PetService petService;

    @Test
    @Transactional
    void createPet() {
        Group group = Group.builder()
                .name("g1")
                .build();
        groupRepository.save(group);
        var group1 = groupRepository.findAll().stream().findAny().get();
        petService.createPet(group1.getId(), "gavs", "desc", Gender.MALE, PetType.DOG);
        var pet = petRepository.findAll().stream().findAny().get();
        Assertions.assertTrue(groupRepository.findById(group1.getId()).get().getPets().contains(petRepository.findById(pet.getId()).get()));
        Assertions.assertEquals(petRepository.findById(pet.getId()).get().getGroup(), groupRepository.findById(group1.getId()).get());
        groupRepository.delete(group1);
        petRepository.delete(pet);
    }

    @Test
    @Transactional
    void updatePet() {
        Group group = Group.builder()
                .name("g1")
                .build();
        groupRepository.save(group);
        var groupFromRepos = groupRepository.findAll().stream().findAny().get();
        Pet pet = Pet.builder()
                .name("Barsik")
                .gender(ru.b19513.pet_schedule.repository.entity.enums.Gender.MALE)
                .type(ru.b19513.pet_schedule.repository.entity.enums.PetType.CAT)
                .group(groupFromRepos)
                .build();
        petRepository.save(pet);
        var petId = petRepository.findAll().stream().findAny().get().getId();
        PetDTO petUpd = PetDTO.builder()
                .id(petId)
                .name("Snezhinka")
                .gender(Gender.FEMALE)
                .type(PetType.CAT)
                .build();
        petService.updatePet(petUpd);
        var newPet = petRepository.findAll().stream().findAny().get();
        Assertions.assertEquals(petMapper.entityToDTO(newPet), petUpd);
        petRepository.delete(newPet);
        groupRepository.delete(group);
    }

    @Test
    void getPets() {
    }

    @Test
    void deletePet() {
    }

    @Test
    void createFeedNote() {
    }

    @Test
    void getFeedNotes() {
    }

    @Test
    void findFeedNotesByDate() {
    }
}