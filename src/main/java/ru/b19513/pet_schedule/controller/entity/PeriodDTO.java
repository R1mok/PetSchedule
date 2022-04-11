package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.time.LocalTime;

@Data
@Builder
@Schema(description = "Период времени")
public class PeriodDTO {
    @Schema(description = "С")
    private LocalTime timeFrom;
    @Schema(description = "По")
    private LocalTime timeTo;
}
