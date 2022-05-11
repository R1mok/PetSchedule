package ru.b19513.pet_schedule.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NameTakenException extends ServiceException {

}
