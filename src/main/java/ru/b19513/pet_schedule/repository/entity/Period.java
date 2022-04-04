package ru.b19513.pet_schedule.repository.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;

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
    private Time timeFrom;

    @Column
    private Time timeTo;
}
