package ru.b19513.pet_schedule.service;

import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.InvitationDTO;
import ru.b19513.pet_schedule.controller.entity.UserDTO;

import java.util.Collection;

public interface UserService {
    UserDTO signInNewUser(String login, String pass, String name);

    UserDTO updateUser(UserDTO user);

    Collection<InvitationDTO> getInvitationByUserId(long id);

    GroupDTO acceptInvintation(long userId, long groupId);

    boolean isLoginFree(String login);
}
