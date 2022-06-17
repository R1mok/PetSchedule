package ru.b19513.pet_schedule.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.b19513.pet_schedule.controller.entity.enums.Gender;
import ru.b19513.pet_schedule.controller.entity.enums.PetType;
import ru.b19513.pet_schedule.repository.*;
import ru.b19513.pet_schedule.repository.entity.*;
import javax.transaction.Transactional;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import ru.b19513.pet_schedule.service.impl.PetServiceImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
                .id(1L)
                .build();
        groupRepository.save(group);
        petService.createPet(1L, "gavs", "desc", Gender.MALE, PetType.DOG);
        Assertions.assertTrue(groupRepository.findById(1L).get().getPets().contains(petRepository.findById(1L).get()));
        Assertions.assertEquals(petRepository.findById(1L).get().getGroup(), groupRepository.findById(1L).get());
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