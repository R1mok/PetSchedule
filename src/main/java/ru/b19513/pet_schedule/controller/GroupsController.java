package ru.b19513.pet_schedule.controller;

import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    @PostMapping("/")
    public ResponseEntity<GroupDTO> create(Principal principal, @RequestParam String name) {
        GroupDTO groupDTO = groupService.createGroup(principal.getName(), name);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Обновление данных группы")
    @PatchMapping("/")
    public ResponseEntity<GroupDTO> update(@RequestParam GroupDTO group, Principal principal) {
        GroupDTO groupDTO = groupService.updateGroup(principal.getName(), group);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Приглашение в группу")
    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<StatusDTO> invite(@PathVariable long groupId, @PathVariable long userId,
            Principal principal) {
        StatusDTO statusDTO = groupService.inviteUser(principal.getName(), groupId, userId);
        return new ResponseEntity<>(statusDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Исключение из группы")
    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupDTO> leave(@PathVariable long groupId, @PathVariable long userId,
            Principal principal) {
        GroupDTO groupDTO = groupService.kickUser(principal.getName(), groupId, userId);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Удаление группы и всех связанных с ней записей")
    @DeleteMapping("/")
    public ResponseEntity<StatusDTO> delete(@RequestParam long groupId, Principal principal) {
        StatusDTO statusDTO = groupService.deleteGroup(groupId, principal.getName());
        return new ResponseEntity<>(statusDTO, HttpStatus.OK);
    }
}
