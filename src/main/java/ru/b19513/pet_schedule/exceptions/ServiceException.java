package ru.b19513.pet_schedule.exceptions;

import lombok.*;

import java.util.function.Supplier;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class ServiceException extends RuntimeException implements Supplier<ServiceException> {
    private String message;

    @Override
    public ServiceException get() {
        return this;
    }
}
