package ru.b19513.pet_schedule.repository.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.b19513.pet_schedule.repository.entity.enums.Gender;
import ru.b19513.pet_schedule.repository.entity.enums.PetType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_PET")
public class Pet {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Group group;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Column
    @Enumerated(EnumType.STRING)
    private PetType type;

    @Column(nullable = true)
    private String description;

    @OneToMany
    @Column
    private Set<Notification> notifications;
}
