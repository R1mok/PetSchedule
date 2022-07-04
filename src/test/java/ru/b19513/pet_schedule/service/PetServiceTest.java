package ru.b19513.pet_schedule.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.b19513.pet_schedule.controller.entity.enums.Gender;
import ru.b19513.pet_schedule.controller.entity.enums.PetType;
import ru.b19513.pet_schedule.repository.GroupRepository;
import ru.b19513.pet_schedule.repository.PetRepository;
import ru.b19513.pet_schedule.repository.entity.Group;

import javax.transaction.Transactional;

@SpringBootTest
class PetServiceTest {

    @Autowired
    PetRepository petRepository;

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
    void updatePet() {
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