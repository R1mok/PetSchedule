package ru.b19513.pet_schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.repository.GroupRepository;
import ru.b19513.pet_schedule.service.GroupService;
import ru.b19513.pet_schedule.service.mapper.GroupMapper;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupMapper groupMapper, GroupRepository groupRepository) {
        this.groupMapper = groupMapper;
        this.groupRepository = groupRepository;
    }
    @Override
    public GroupDTO createGroup(long userId, String name) {
        return null;
    }

    @Override
    public GroupDTO updateGroup(GroupDTO group) {
        return null;
    }

    @Override
    public StatusDTO inviteUser(long groupId, long userId) {
        return null;
    }

    @Override
    public StatusDTO kickUser(long groupId, long userId) {
        return null;
    }

    @Override
    public StatusDTO deleteGroup(long groupId, long ownerId) {
        return null;
    }
}
