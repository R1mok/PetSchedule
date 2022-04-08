package ru.b19513.pet_schedule.repository.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_schedule_time")
public class ScheduleTime {
    @Id
    private long id;

    @Column
    private LocalTime notifTime;
}
