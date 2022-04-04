package ru.b19513.pet_schedule.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_notification_timeout")
public class NotificationTimeout {

    public NotificationTimeout(Notification notification, int elapsed){
        this.id = notification.getId();
        this.elapsed = elapsed;
        this.notification = notification;
    }

    @Id
    private long id;

    @Column
    private int elapsed;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Period> times;
}