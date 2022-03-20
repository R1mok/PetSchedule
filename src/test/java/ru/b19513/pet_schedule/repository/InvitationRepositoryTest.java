package ru.b19513.pet_schedule.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.b19513.pet_schedule.repository.entity.Group;
import ru.b19513.pet_schedule.repository.entity.Invitation;
import ru.b19513.pet_schedule.repository.entity.User;

import java.util.List;

import static ru.b19513.pet_schedule.repository.entity.enums.Gender.MALE;

@DataJpaTest
class InvitationRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    InvitationRepository invitationRepository;

    @Test
    void insertExists() {
        User user1 = User.builder()
                .login("lol")
                .name("лол")
                .gender(MALE)
                .build();
        User user2 = User.builder()
                .login("kek")
                .name("кек")
                .gender(MALE)
                .build();

        userRepository.saveAll(List.of(user1, user2));

        Group gr1 = Group.builder()
                .owner(user1)
                .name("группа 1")
                .build();
        groupRepository.save(gr1);

        Invitation inv1 = new Invitation(user1, gr1);
        invitationRepository.save(inv1);
        System.out.println(invitationRepository.findAll());
        Invitation inv2 = new Invitation(user1, gr1);
        invitationRepository.save(inv2);
        System.out.println(invitationRepository.findAll());
        System.out.println("-------------------------");
        System.out.println(inv1);
        System.out.println(inv2);
    }
}