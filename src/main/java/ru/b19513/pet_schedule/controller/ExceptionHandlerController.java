package ru.b19513.pet_schedule.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.exceptions.LoginBusyException;
import ru.b19513.pet_schedule.exceptions.NotFoundException;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<StatusDTO> handleNotFound(NotFoundException exception) {
        return new ResponseEntity<>(StatusDTO.builder()
                .status(HttpStatus.NOT_FOUND)
                .description(exception.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = LoginBusyException.class)
    protected ResponseEntity<StatusDTO> handleLoginBusy(LoginBusyException exception) {
        return new ResponseEntity<>(StatusDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .description(exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
