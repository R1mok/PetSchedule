package ru.b19513.pet_schedule.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.service.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupsController {

    private final GroupService groupService;
    @Autowired
    public GroupsController(GroupService groupService) {
        this.groupService = groupService;
    }

    @ApiOperation(value = "Создание новой группы")
    @PostMapping("/{userId}{name}")
    public ResponseEntity<GroupDTO> create(@PathVariable long userId, @PathVariable String name) {
        GroupDTO groupDTO = groupService.createGroup(userId, name);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Обновление данных группы")
    @PutMapping("/")
    public ResponseEntity<GroupDTO> update(@RequestParam GroupDTO group) {
        GroupDTO groupDTO = groupService.updateGroup(group);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Приглашение в группу")
    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<StatusDTO> invite(@PathVariable long groupId, @PathVariable long userId) {
        StatusDTO statusDTO = groupService.inviteUser(groupId, userId);
        return new ResponseEntity<>(statusDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Исключение из группы")
    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<StatusDTO> leave(@PathVariable long groupId, @PathVariable long userId) {
        StatusDTO statusDTO = groupService.kickUser(groupId, userId);
        return new ResponseEntity<>(statusDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Удаление группы и всех связанных с ней записей")
    @DeleteMapping("/{groupId}{ownerId}")
    public ResponseEntity<StatusDTO> delete(@PathVariable long groupId, @PathVariable long ownerId) {
        StatusDTO statusDTO = groupService.deleteGroup(groupId, ownerId);
        return new ResponseEntity<>(statusDTO, HttpStatus.OK);
    }
}
