package ru.b19513.pet_schedule.service;

import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;

public interface GroupService {

    GroupDTO createGroup(String senderLogin, String name);

    GroupDTO updateGroup(String senderLogin, GroupDTO group);

    StatusDTO inviteUser(String senderLogin,long groupId, long userId);

    GroupDTO kickUser(String senderLogin, long groupId, long userId);

    StatusDTO deleteGroup(long groupId, String ownerLogin);
}
