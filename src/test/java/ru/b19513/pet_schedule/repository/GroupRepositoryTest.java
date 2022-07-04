package ru.b19513.pet_schedule.repository;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.b19513.pet_schedule.repository.entity.*;
import ru.b19513.pet_schedule.repository.entity.enums.Gender;


@DataJpaTest
class GroupRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Test
    void createGroup() {
        val u1 = User.builder()
                .login("R1mok")
                .name("Антон")
                .gender(Gender.MALE)
                .password("jfmskadmlksa")
                .build();
        val g1 = Group.builder()
                .name("g1")
                .owner(u1)
                .build();
        userRepository.save(u1);
        groupRepository.save(g1);
        var group = groupRepository.findById(1L).get();
        Assertions.assertEquals(g1, group);
    }
}