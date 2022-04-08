package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

@Data
@Builder
@Schema(description = "Уведомление типа Schedule")
public class NotificationScheduleDTO implements Serializable {
    @Schema(description = "Массив времен, когда нужно нужно посылать уведомление")
    private List<Time> arrayOfTimes;
}
