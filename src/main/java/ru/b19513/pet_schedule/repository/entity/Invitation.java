package ru.b19513.pet_schedule.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "t_invitation")
public class Invitation {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private long id;

    @NaturalId
    @ManyToOne
    private User user;

    @NaturalId
    @ManyToOne
    private Group group;
}
