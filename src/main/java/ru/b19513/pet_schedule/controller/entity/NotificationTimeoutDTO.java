package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.b19513.pet_schedule.repository.entity.Period;

import java.util.List;

@Data
@Builder
@Schema(description = "Уведомление типа Timeout")
public class NotificationTimeoutDTO {
    @Schema(description = "Время, через которое нужно послать уведомление (в секундах)")
    private long elapsed;
    @Schema(description = "Список периодов, когда не нужно посылать уведомления")
    private List<Period> periods;
}
