package ru.b19513.pet_schedule.service;

import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;

public interface GroupService {

    GroupDTO createGroup(long userId, String name);

    GroupDTO updateGroup(GroupDTO group);

    GroupDTO inviteUser(long groupId, long userId);

    GroupDTO kickUser(long groupId, long userId);

    StatusDTO deleteGroup(long groupId, long ownerId);
}
