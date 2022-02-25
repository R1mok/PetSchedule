package ru.b19513.pet_schedule.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "T_FEED_NOTE")
public class FeedNote {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Pet pet;

    @Column
    private LocalDateTime dateTime;
}
