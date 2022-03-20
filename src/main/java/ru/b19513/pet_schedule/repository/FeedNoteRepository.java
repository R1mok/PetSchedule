package ru.b19513.pet_schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.b19513.pet_schedule.repository.entity.FeedNote;
import ru.b19513.pet_schedule.repository.entity.Pet;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FeedNoteRepository extends JpaRepository<FeedNote, Long> {
    List<FeedNote> findByPetAndDateTimeIsBetween(Pet pet, LocalDateTime dateTime, LocalDateTime dateTime2);
    List<FeedNote> findByPet(Pet pet);
}
