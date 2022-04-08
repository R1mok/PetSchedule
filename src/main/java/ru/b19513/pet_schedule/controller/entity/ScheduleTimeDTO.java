package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
@Schema(description = "Время в уведомлении типа Schedule")
public class ScheduleTimeDTO {
    @Schema(description = "Время уведомления")
    private LocalTime notifTime;
}
