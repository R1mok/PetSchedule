package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Schema(description = "Уведомление типа Schedule")
public class NotificationScheduleDTO extends NotificationDTO {

    @Builder
    public NotificationScheduleDTO(long id, long groupId, boolean enabled, String comment,
            List<LocalTime> times) {
        super(id, groupId, enabled, comment);
        this.times = times;
    }

    @Schema(description = "Массив времен, когда нужно нужно посылать уведомление")
    private List<LocalTime> times;
}
