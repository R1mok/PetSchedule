package ru.b19513.pet_schedule.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "t_notification")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Notification {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column
    private boolean enabled;

    @Column
    private String comment;

    @Column
    @OneToMany(mappedBy = "notification")
    private List<NotificationNote> notificationNotes;

    @JoinColumn
    @ManyToOne
    private Group group;

    @ManyToOne
    @JoinColumn
    private Pet pet;
}
