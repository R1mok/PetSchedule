package ru.b19513.pet_schedule.repository;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.b19513.pet_schedule.repository.entity.User;
import ru.b19513.pet_schedule.repository.entity.enums.Gender;

import java.util.List;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository repo;

    @Test
    void testRepo() {
        val U1 = User.builder()
                .login("Jawa")
                .name("Джава")
                .gender(Gender.MALE)
                .passwordHash(new byte[]{42, 43})
                .about("Поделил на ноль")
                .build();
        val U2 = User.builder()
                .login("vantuz")
                .name("Иванесса")
                .gender(Gender.FEMALE)
                .passwordHash(new byte[]{44, 45})
                .build();
        repo.save(U1);
        repo.save(U2);
        var ret = repo.findAll();
        val excepted = List.of(U1, U2);

        Assertions.assertIterableEquals(excepted, ret);
    }
}