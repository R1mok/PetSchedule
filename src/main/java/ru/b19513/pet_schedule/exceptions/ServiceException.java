package ru.b19513.pet_schedule.exceptions;

import lombok.*;

import java.util.function.Supplier;

@Setter
@Getter
public class ServiceException extends RuntimeException implements Supplier<ServiceException> {
    private String message;
    public ServiceException(String mes){
        this.message = mes;
    }
    @Override
    public ServiceException get() {
        return this;
    }
}
