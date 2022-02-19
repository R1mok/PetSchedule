package ru.b19513.pet_schedule.web;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ru.b19513.pet_schedule.consts.Consts.NOT_IMPLEMENTED;

@RestController
@RequestMapping("/pets")
public class PetsController {

    @ApiOperation(value = "Получить питомца по id")
    @GetMapping("/{petId}")
    public ResponseEntity<String> getPet(@PathVariable String petId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Получить список питомцев по id группы")
    @GetMapping
    public ResponseEntity<String> getPets(@RequestParam String groupId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Добавить нового питомца")
    @PostMapping
    public ResponseEntity<String> newPet() {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Обновить сведения о питомце по id")
    @PostMapping("/{petId}")
    public ResponseEntity<String> updatePet(@PathVariable String petId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Удалить питомца и все связанные с ним напоминания и записи")
    @DeleteMapping("/{petId}")
    public ResponseEntity<String> deletePet(@PathVariable String petId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }
}
