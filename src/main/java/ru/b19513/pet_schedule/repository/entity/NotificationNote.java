package ru.b19513.pet_schedule.repository.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_notificationNotes")
public class NotificationNote {

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Key implements Serializable {

        @Column(name = "user_id")
        private long userId;

        @Column(name = "notif_id")
        private long notifId;
    }

    @EmbeddedId
    private Key key;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "notif_id", insertable = false, updatable = false)
    private Notification notification;

    @Column
    private LocalDateTime lastTime;
}
