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
public class NotificationTimeout extends Notification {

    @Column
    private long elapsed;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Period> times;
}