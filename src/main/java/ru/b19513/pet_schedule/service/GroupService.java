package ru.b19513.pet_schedule.service;

import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.repository.entity.User;

public interface GroupService {

    GroupDTO createGroup(User owner, String name);

    GroupDTO updateGroup(User owner, GroupDTO group);

    StatusDTO inviteUser(User owner,long groupId, long userId);

    GroupDTO kickUser(User owner, long groupId, long userId);

    StatusDTO deleteGroup(long groupId, User owner);
}
