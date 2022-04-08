package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;

@Data
@Builder
@Schema(description = "Период времени")
public class PeriodDTO implements Serializable {
    @Schema(description = "С")
    private Time timeFrom;
    @Schema(description = "По")
    private Time timeTo;
}
