package ru.b19513.pet_schedule.repository.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_invitation")
public class Invitation {

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Key implements Serializable {

        @Column(name = "user_id")
        private long userId;

        @Column(name = "group_id")
        private long groupId;
    }
    public Invitation(@NotNull User user,@NotNull Group group)
    {
        this.user = user;
        this.group = group;
        key = new Key(user.getId(), group.getId());
    }

    @EmbeddedId
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private Group group;
}

