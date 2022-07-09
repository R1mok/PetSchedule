package ru.b19513.pet_schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.exceptions.NotFoundException;
import ru.b19513.pet_schedule.exceptions.NotPermittedException;
import ru.b19513.pet_schedule.repository.GroupRepository;
import ru.b19513.pet_schedule.repository.InvitationRepository;
import ru.b19513.pet_schedule.repository.UserRepository;
import ru.b19513.pet_schedule.repository.entity.Group;
import ru.b19513.pet_schedule.repository.entity.Invitation;
import ru.b19513.pet_schedule.repository.entity.User;
import ru.b19513.pet_schedule.service.GroupService;
import ru.b19513.pet_schedule.service.mapper.GroupMapper;

import java.util.HashSet;

import static ru.b19513.pet_schedule.consts.Consts.*;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;

    @Autowired
    public GroupServiceImpl(GroupMapper groupMapper, GroupRepository groupRepository, UserRepository userRepository,
                            InvitationRepository invitationRepository) {
        this.groupMapper = groupMapper;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
    }

    @Override
    public GroupDTO createGroup(User owner, String name) {
        var group = Group.builder()
                .name(name)
                .owner(owner)
                .build();
        group.setUsers(new HashSet<>());
        group.getUsers().add(owner);
        return groupMapper.entityToDTO(groupRepository.save(group));
    }

    @Override
    public GroupDTO updateGroup(User owner, GroupDTO groupDTO) {
        var group = groupRepository.findById(groupDTO.getId()).
                orElseThrow(new NotFoundException("Group with group id " + groupDTO.getId() + " not found"));
        if (group.getOwner().getId() != owner.getId()) {
            throw new NotPermittedException("User with id " + owner.getId() + " does not have permission");
        }
        groupMapper.updateEntity(group, groupDTO);
        return groupMapper.entityToDTO(groupRepository.save(group));
    }

    @Override
    public StatusDTO inviteUser(User owner, long groupId, long userId) {
        var group = groupRepository.findById(groupId)
                .orElseThrow(new NotFoundException("Group with group id " + groupId + " not found"));
        if (group.getOwner().getId() != owner.getId()) // только создатель группы может рассылать приглашения
        {
            throw new NotPermittedException("User with id " + owner.getId() + " does not have permission");
        }
        var user = userRepository.findById(userId)
                .orElseThrow(new NotFoundException("User with user id " + userId + " not found"));
        // Если приглашение уже есть в БД - ничего не поменяется
        var invInRepo = invitationRepository.save(new Invitation(user, group));
        if (user.getInvitations() == null) {
            user.setInvitations(new HashSet<>());
        }
        user.getInvitations().add(invInRepo);
        return StatusDTO.builder()
                .status(HttpStatus.OK)
                .description(INVITATION_SENDED)
                .build();
    }

    @Override
    public GroupDTO kickUser(User owner, long groupId, long userId) {
        var group = groupRepository.findById(groupId)
                .orElseThrow(new NotFoundException("Group with group id " + groupId + " not found"));
        if (group.getOwner().getId() != owner.getId()) {
            throw new NotPermittedException("User with id " + owner.getId() + " does not have permission");
        }
        var user = userRepository.findById(userId)
                .orElseThrow(new NotFoundException("User with user id " + userId + " not found"));
        group.getUsers().remove(user);
        return groupMapper.entityToDTO(groupRepository.save(group));
    }

    @Override
    public StatusDTO deleteGroup(long groupId, User owner) {
        var group = groupRepository.findById(groupId)
                .orElseThrow(new NotFoundException("Group with group id " + groupId + " not found"));
        if (group.getOwner().getId() != owner.getId()) {
            throw new NotPermittedException("User with id " + owner.getId() + " does not have permission");
        }
        groupRepository.delete(group);
        return StatusDTO.builder()
                .status(HttpStatus.OK)
                .description(GROUP_DELETED)
                .build();
    }
}
