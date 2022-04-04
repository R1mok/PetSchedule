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
public class Notification {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private long id;

    @ManyToOne
    private Group group;

    @Column
    private boolean enabled;

    @Column
    private String comment;

    @Column
    @OneToMany(mappedBy = "notification")
    private List<NotificationNote> notificationNotes;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "notification_schedule_id")
    private NotificationSchedule notificationSchedule;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "notification_timeout_id")
    private NotificationTimeout notificationTimeout;
}
