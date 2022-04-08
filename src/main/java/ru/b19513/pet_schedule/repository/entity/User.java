package ru.b19513.pet_schedule.repository.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import ru.b19513.pet_schedule.repository.entity.enums.Gender;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_USER")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NaturalId
    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String name;

    @Column
    private String about;

    @Column
    private byte[] passwordHash;

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
