package ru.b19513.pet_schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.b19513.pet_schedule.repository.entity.FeedNote;

@Repository
public interface FeedNoteRepository extends JpaRepository<FeedNote, Long> {
}
