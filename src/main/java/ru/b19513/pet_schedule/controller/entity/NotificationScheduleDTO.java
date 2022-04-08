package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Schema(description = "Уведомление типа Schedule")
public class NotificationScheduleDTO extends NotificationDTO{
    @Schema(description = "Массив времен, когда нужно нужно посылать уведомление")
    private List<LocalTime> times;
}
