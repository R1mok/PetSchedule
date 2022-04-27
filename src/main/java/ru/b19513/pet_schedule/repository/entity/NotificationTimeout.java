package ru.b19513.pet_schedule.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_notification_timeout")
public class NotificationTimeout extends Notification {

    @Builder
    public NotificationTimeout(Group group, Pet pet, String comment, long elapsed, boolean enabled){
        super(group, pet, comment, enabled);
        this.elapsed = elapsed;

    }
    @Column
    private long elapsed;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "notification")
    private List<Period> times;
}