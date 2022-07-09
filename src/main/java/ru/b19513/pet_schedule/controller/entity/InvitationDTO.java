package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.b19513.pet_schedule.repository.entity.Group;
import ru.b19513.pet_schedule.repository.entity.User;

import java.io.Serializable;
@Data
@Builder
@Schema(description = "Приглашение пользователя в группу")
public class InvitationDTO implements Serializable {
    @Schema(description = "Id пользователя, которого нужно пригласить в группу")
    private Long userId;
    @Schema(description = "Группа, в которую приглашается пользователь")
    private GroupDTO group;
}
