package ru.b19513.pet_schedule.web;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ru.b19513.pet_schedule.consts.Consts.NOT_IMPLEMENTED;

@RestController
@RequestMapping("/groups")
public class GroupsController {


    @ApiOperation(value = "Создание новой группы")
    @PostMapping("/")
    public ResponseEntity<String> create() {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Обновление данных группы")
    @PutMapping("/{groupId}")
    public ResponseEntity<String> update(@PathVariable String groupId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Приглашение в группу")
    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<String> invite(@PathVariable String groupId, @PathVariable String userId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Исключение из группы")
    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<String> leave(@PathVariable String groupId, @PathVariable String userId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Удаление группы и всех связанных с ней записей")
    @DeleteMapping("/{groupId}")
    public ResponseEntity<String> delete(@PathVariable String groupId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }
}
