package ru.b19513.pet_schedule.service.impl;

import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.service.GroupService;

public class GroupServiceImpl implements GroupService {
    @Override
    public GroupDTO createGroup(long userId, String name) {
        return null;
    }

    @Override
    public GroupDTO updateGroup(GroupDTO group) {
        return null;
    }

    @Override
    public GroupDTO inviteUser(long groupId, long userId) {
        return null;
    }

    @Override
    public GroupDTO kickUser(long groupId, long userId) {
        return null;
    }

    @Override
    public StatusDTO deleteGroup(long groupId, long ownerId) {
        return null;
    }
}
