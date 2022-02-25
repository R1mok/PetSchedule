package ru.b19513.pet_schedule.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.b19513.pet_schedule.repository.entity.enums.Gender;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "T_USER")
public class User {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String about;

    @Column
    private long passwordHash;

    @Column
    @ManyToMany(mappedBy = "users")
    private Set<Group> groups;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private Set<Group> ownedGroups;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Column
    @OneToMany(mappedBy = "group")
    private Set<Invitation> invitations;
}
