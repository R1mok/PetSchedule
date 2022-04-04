package ru.b19513.pet_schedule.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_notification_schedule")
public class NotificationSchedule {

    public NotificationSchedule(Notification notification, List<LocalDateTime> arrayOfTimes){
        this.id = notification.getId();
        this.notification = notification;
        this.arrayOfTimes = arrayOfTimes;
    }
    @Id
    private long id;

    @ElementCollection
    private List<LocalDateTime> arrayOfTimes;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;
}