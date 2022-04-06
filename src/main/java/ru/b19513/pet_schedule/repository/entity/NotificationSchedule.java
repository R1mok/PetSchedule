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
public class NotificationSchedule extends Notification {

    @ElementCollection
    private List<LocalDateTime> arrayOfTimes;

}