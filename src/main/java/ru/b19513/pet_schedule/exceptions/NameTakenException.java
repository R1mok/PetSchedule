package ru.b19513.pet_schedule.exceptions;

public class NameTakenException extends ServiceException {
    public NameTakenException(String s) {
        super(s);
    }
}
