package ru.b19513.pet_schedule.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.b19513.pet_schedule.controller.entity.PetDTO;
import ru.b19513.pet_schedule.controller.entity.enums.Gender;
import ru.b19513.pet_schedule.controller.entity.enums.PetType;
import ru.b19513.pet_schedule.repository.FeedNoteRepository;
import ru.b19513.pet_schedule.repository.GroupRepository;
import ru.b19513.pet_schedule.repository.PetRepository;
import ru.b19513.pet_schedule.repository.UserRepository;
import ru.b19513.pet_schedule.repository.entity.Group;
import ru.b19513.pet_schedule.repository.entity.Pet;
import ru.b19513.pet_schedule.repository.entity.User;
import ru.b19513.pet_schedule.service.PetService;
import ru.b19513.pet_schedule.service.mapper.FeedNoteMapper;
import ru.b19513.pet_schedule.service.mapper.PetMapper;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;


@SpringBootTest
class PetServiceImplTest {
    @Autowired
    PetRepository petRepository;
    @Autowired
    FeedNoteRepository feedNoteRepository;
    @Autowired
    PetMapper petMapper;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PetService petService;
    @Autowired
    FeedNoteMapper feedNoteMapper;

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
        groupRepository.deleteAll();
        petRepository.deleteAll();
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
        petRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    @Transactional
    void getPets() {
        Group group = Group.builder()
                .name("group")
                .build();
        groupRepository.save(group);
        var groupInRepo = groupRepository.findAll().stream().findAny().get();
        petService.createPet(groupInRepo.getId(), "Murka", "", Gender.FEMALE, PetType.CAT);
        petService.createPet(groupInRepo.getId(), "Barsik", "", Gender.MALE, PetType.CAT);
        var pets = new HashSet<>(petRepository.findAll());
        Assertions.assertEquals(petMapper.entityToDTO(pets),
                petService.getPets(groupInRepo.getId()));
        Assertions.assertEquals(2, pets.stream().map(e -> e.getGroup().equals(group)).count());
        petRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    @Transactional
    void deletePet() {
        Group group = Group.builder()
                .name("group")
                .build();
        groupRepository.save(group);
        var groupInRepo = groupRepository.findAll().get(0);
        petService.createPet(groupInRepo.getId(), "Barsik", "", Gender.MALE, PetType.CAT);
        var pet = petRepository.findAll().stream().findAny().get();
        Assertions.assertEquals(1, petService.getPets(groupInRepo.getId()).size());
        petService.deletePet(pet.getId());
        Assertions.assertEquals(0, petService.getPets(groupInRepo.getId()).size());
        petRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    @Transactional
    void createFeedNote() {
        Group group = Group.builder()
                .name("group")
                .build();
        groupRepository.save(group);
        User user = User.builder()
                .login("R1mok")
                .name("Anton")
                .password("1234")
                .build();
        userRepository.save(user);
        var userInRepo = userRepository.findAll().get(0);
        var groupInRepo = groupRepository.findAll().stream().findAny().get();
        petService.createPet(groupInRepo.getId(), "Barsik", "", Gender.MALE, PetType.CAT);
        var petInRepo = petRepository.findAll().get(0);
        petService.createFeedNote(petInRepo.getId(), userInRepo.getId(), "Feed Barsik");
        Assertions.assertEquals(petInRepo, feedNoteRepository.findByPetId(petInRepo.getId()).get(0).getPet());
        Assertions.assertEquals(userInRepo, feedNoteRepository.findByPetId(petInRepo.getId()).get(0).getUser());
        petRepository.deleteAll();
        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void getFeedNotes() {
        // test 1 feednote
        Group group = Group.builder()
                .name("group")
                .build();
        groupRepository.save(group);
        User user = User.builder()
                .login("R1mok")
                .name("Anton")
                .password("1234")
                .build();
        userRepository.save(user);
        var userInRepo = userRepository.findAll().get(0);
        var groupInRepo = groupRepository.findAll().stream().findAny().get();
        petService.createPet(groupInRepo.getId(), "Barsik", "", Gender.MALE, PetType.CAT);
        var petInRepo = petRepository.findAll().get(0);
        petService.createFeedNote(petInRepo.getId(), userInRepo.getId(), "Feed Barsik");
        Assertions.assertEquals(feedNoteMapper.entityToDTO(feedNoteRepository.findByPetId(petInRepo.getId()).get(0)),
                petService.getFeedNotes(petInRepo.getId()).stream().findAny().get());
        // test 2 feednotes
        Group group1 = Group.builder()
                .name("group1")
                .build();
        groupRepository.save(group1);
        var groupInRepo1 = groupRepository.findAll().get(1);
        petService.createFeedNote(petInRepo.getId(), userInRepo.getId(), "Feed Barsik again");
        Assertions.assertEquals(2, petService.getFeedNotes(petInRepo.getId()).size());
        Assertions.assertEquals(feedNoteMapper.entityToDTO(feedNoteRepository.findByPetId(petInRepo.getId()).get(0)),
                petService.getFeedNotes(petInRepo.getId()).toArray()[0]);
        Assertions.assertEquals(feedNoteMapper.entityToDTO(feedNoteRepository.findByPetId(petInRepo.getId()).get(1)),
                petService.getFeedNotes(petInRepo.getId()).toArray()[1]);
        // test 2 pets with feednotes
        petService.createPet(group1.getId(), "Murka", "", Gender.FEMALE, PetType.CAT);
        var pet1InRepo = petRepository.findAll().get(1);
        petService.createFeedNote(pet1InRepo.getId(), userInRepo.getId(), "Feed Murka");
        Assertions.assertEquals(feedNoteMapper.entityToDTO(feedNoteRepository.findByPetId(pet1InRepo.getId()).get(0)),
                petService.getFeedNotes(pet1InRepo.getId()).stream().findAny().get());
        petRepository.deleteAll();
        groupRepository.deleteAll();
        userRepository.deleteAll();
        feedNoteRepository.deleteAll();
    }

    @Test
    @Transactional
    void findFeedNotesByDate() throws InterruptedException {
        Group group = Group.builder()
                .name("group")
                .build();
        groupRepository.save(group);
        User user = User.builder()
                .login("R1mok")
                .name("Anton")
                .password("1234")
                .build();
        userRepository.save(user);
        var userInRepo = userRepository.findAll().get(0);
        var groupInRepo = groupRepository.findAll().stream().findAny().get();
        petService.createPet(groupInRepo.getId(), "Barsik", "", Gender.MALE, PetType.CAT);
        var petInRepo = petRepository.findAll().get(0);
        petService.createFeedNote(petInRepo.getId(), userInRepo.getId(), "Feed Barsik");
        // get 1 feednote
        var imaginaryTime = LocalDateTime.of(2017, 10, 22, 16, 30);
        //var imaginaryTime = LocalDateTime.MIN;
        Thread.sleep(1000);
        var feednoteList = petService.findFeedNotesByDate(petInRepo.getId(),
                imaginaryTime, LocalDateTime.now());
        Assertions.assertEquals(feedNoteMapper.entityToDTO(feedNoteRepository.findByPetId(petInRepo.getId())),
                feednoteList);
        // get 1 feednote from suitable interval
        var currentTime = LocalDateTime.now();
        Thread.sleep(1000);
        petService.createFeedNote(petInRepo.getId(), userInRepo.getId(), "Feed barsik 2nd time");
        feednoteList = petService.findFeedNotesByDate(petInRepo.getId(), imaginaryTime, currentTime);
        Assertions.assertEquals(1, feednoteList.size());
        Thread.sleep(1000);
        feednoteList = petService.findFeedNotesByDate(petInRepo.getId(), imaginaryTime, LocalDateTime.now());
        Assertions.assertEquals(feedNoteMapper.entityToDTO(feedNoteRepository.findByPetId(petInRepo.getId())),
                feednoteList);
        petRepository.deleteAll();
        groupRepository.deleteAll();
        userRepository.deleteAll();
        feedNoteRepository.deleteAll();
    }
}