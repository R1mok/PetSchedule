package ru.b19513.pet_schedule.repository.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_notification")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Notification {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private long id;

    @Column
    private boolean enabled;

    @Column
    private String comment;

    @Column
    @OneToMany(mappedBy = "notification")
    private List<NotificationNote> notificationNotes;
}
