package ru.b19513.pet_schedule.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "T_GROUP")
public class Group {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToOne
    private User owner;

    @ManyToMany
    @JoinTable
            (
                    joinColumns = {@JoinColumn(name = "group_id", nullable = false)},
                    inverseJoinColumns = {@JoinColumn(name = "user_id", nullable = false)}
            )
    private Set<User> users;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<Pet> pets;

    @Column
    @OneToMany(mappedBy = "group")
    private Set<Invitation> invitations;
}
