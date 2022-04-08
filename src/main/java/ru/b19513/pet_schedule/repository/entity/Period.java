package ru.b19513.pet_schedule.repository.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_period")
public class Period {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    private long id;

    @Column
    private LocalTime timeFrom;

    @Column
    private LocalTime timeTo;

    @ManyToOne
    @JoinColumn(name = "notif_id")
    private NotificationTimeout notificationTimeout;
}
