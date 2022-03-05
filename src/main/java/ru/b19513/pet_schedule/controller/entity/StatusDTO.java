package ru.b19513.pet_schedule.controller.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@Schema(description = "Статус выполнения операции")
public class StatusDTO {
    @Schema(description = "Код ответа", example = "404")
    private HttpStatus status;
    @Schema(description = "Описание результата", example = "Пользователь с данным id не найден")
    private String description;
}
