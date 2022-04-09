package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Абстрактный класс уведомления")
public abstract class NotificationDTO {
    @Schema(description = "Id уведомления")
    private long id;
    @Schema(description = "Id группы, в которой есть это уведомление")
    private long groupId;
    @Schema(description = "Активно ли уведомление")
    private boolean enabled;
    @Schema(description = "Комментарий к уведомлению")
    private String comment;
}
