package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.b19513.pet_schedule.repository.entity.ScheduleTime;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Schema(description = "Уведомление типа Schedule")
public class NotificationScheduleDTO extends NotificationDTO{
    @Schema(description = "Массив времен, когда нужно нужно посылать уведомление")
    private List<ScheduleTime> times;
}
