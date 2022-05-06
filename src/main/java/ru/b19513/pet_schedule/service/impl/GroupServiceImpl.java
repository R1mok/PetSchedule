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

import static ru.b19513.pet_schedule.consts.Consts.GROUP_DELETED;
import static ru.b19513.pet_schedule.consts.Consts.INVITATION_SENDED;

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
        return groupMapper.entityToDTO(groupRepository.save(group));
    }

    @Override
    public GroupDTO updateGroup(User owner, GroupDTO groupDTO) {
        var group = groupRepository.findById(groupDTO.getId()).orElseThrow(NotFoundException::new);
        if (group.getOwner().getId() != owner.getId()) {
            throw new NotPermittedException();
        }
        groupMapper.updateEntity(group, groupDTO);
        return groupMapper.entityToDTO(groupRepository.save(group));
    }

    @Override
    public StatusDTO inviteUser(User owner, long groupId, long userId) {
        var group = groupRepository.findById(groupId).orElseThrow(NotFoundException::new);
        if (group.getOwner().getId() != owner.getId()) // только создатель группы может рассылать приглашения
        {
            throw new NotPermittedException();
        }
        var user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        // Если приглашение уже есть в БД - ничего не поменяется
        var inv = new Invitation(user, group);
        invitationRepository.save(inv);

        return StatusDTO.builder()
                .status(HttpStatus.OK)
                .description(INVITATION_SENDED)
                .build();
    }

    @Override
    public GroupDTO kickUser(User owner, long groupId, long userId) {
        var group = groupRepository.findById(groupId).orElseThrow(NotFoundException::new);
        if (group.getOwner().getId() != owner.getId()) {
            throw new NotPermittedException();
        }
        var user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        group.getUsers().remove(user);
        return groupMapper.entityToDTO(groupRepository.save(group));
    }

    @Override
    public StatusDTO deleteGroup(long groupId, User owner) {
        var group = groupRepository.findById(groupId).orElseThrow(NotFoundException::new);
        if (group.getOwner().getId() != owner.getId()) {
            throw new NotPermittedException();
        }
        groupRepository.delete(group);
        return StatusDTO.builder()
                .status(HttpStatus.OK)
                .description(GROUP_DELETED)
                .build();
    }
}
