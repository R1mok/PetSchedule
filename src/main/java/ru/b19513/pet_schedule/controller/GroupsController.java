package ru.b19513.pet_schedule.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.repository.entity.User;
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
    @PostMapping("/")
    public ResponseEntity<GroupDTO> create(Authentication auth, @RequestParam String name) {
        GroupDTO groupDTO = groupService.createGroup((User) auth.getPrincipal(), name);
        return ResponseEntity.ok(groupDTO);
    }

    @ApiOperation(value = "Обновление данных группы")
    @PatchMapping("/")
    public ResponseEntity<GroupDTO> update(Authentication auth, @RequestParam GroupDTO group) {
        GroupDTO groupDTO = groupService.updateGroup((User) auth.getPrincipal(), group);
        return ResponseEntity.ok(groupDTO);
    }

    @ApiOperation(value = "Приглашение в группу")
    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<StatusDTO> invite(Authentication auth, @PathVariable long groupId, @PathVariable long userId) {
        StatusDTO statusDTO = groupService.inviteUser((User) auth.getPrincipal(), groupId, userId);
        return ResponseEntity.ok(statusDTO);
    }

    @ApiOperation(value = "Исключение из группы")
    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupDTO> leave(Authentication auth, @PathVariable long groupId, @PathVariable long userId) {
        GroupDTO groupDTO = groupService.kickUser((User) auth.getPrincipal(), groupId, userId);
        return ResponseEntity.ok(groupDTO);
    }

    @ApiOperation(value = "Удаление группы и всех связанных с ней записей")
    @DeleteMapping("/")
    public ResponseEntity<StatusDTO> delete(Authentication auth, @RequestParam long groupId) {
        StatusDTO statusDTO = groupService.deleteGroup(groupId, (User) auth.getPrincipal());
        return ResponseEntity.ok(statusDTO);
    }
}
