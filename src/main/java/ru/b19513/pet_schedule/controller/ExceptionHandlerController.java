package ru.b19513.pet_schedule.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.exceptions.*;

import java.rmi.server.ServerCloneException;

@Log4j2
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<StatusDTO> handleNotFound(NotFoundException exception) {
        log.info("Not found exception for class {}: {}",
                exception.getClass().toString(), exception.getMessage());
        return new ResponseEntity<>(StatusDTO.builder()
                .status(HttpStatus.NOT_FOUND)
                .description(exception.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = LoginBusyException.class)
    protected ResponseEntity<StatusDTO> handleLoginBusy(LoginBusyException exception) {
        log.info("Login busy exception for class {}:\n {}",
                exception.getClass(), exception.getMessage());
        return new ResponseEntity<>(StatusDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .description(exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = NameTakenException.class)
    protected ResponseEntity<StatusDTO> handleNameTaken(NameTakenException exception){
        log.info("Name taken exception for class {}:\n {}",
                exception.getClass(), exception.getMessage());
        return new ResponseEntity<>(StatusDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .description(exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotPermittedException.class)
    protected ResponseEntity<StatusDTO> handleNotPermitted(NotPermittedException exception){
        log.info("Not permitted exception for class {}:\n {}",
                exception.getClass(), exception.getMessage());
        return new ResponseEntity<>(StatusDTO.builder()
                .status(HttpStatus.FORBIDDEN)
                .description(exception.getMessage())
                .build(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = WrongNotificationClassException.class)
    protected ResponseEntity<StatusDTO> handleWrongNotificationClass(WrongNotificationClassException exception){
        log.info("Wrong notification class exception for class {}:\n {}",
                exception.getClass(), exception.getMessage());
        return new ResponseEntity<>(StatusDTO.builder()
                .status(HttpStatus.NOT_ACCEPTABLE)
                .description(exception.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<StatusDTO> handleConflict(Exception exception){
        log.catching(exception);
        return new ResponseEntity<>(StatusDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .description(exception.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
