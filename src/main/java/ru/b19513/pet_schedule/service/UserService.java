package ru.b19513.pet_schedule.service;

import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.InvitationDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.controller.entity.UserDTO;
import ru.b19513.pet_schedule.repository.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserService{
    UserDTO signInNewUser(String login, String pass, String name);

    UserDTO updateUser(User user, UserDTO userDTO);

    Collection<InvitationDTO> getInvitation(User user);

    GroupDTO acceptInvintation(User user, long groupId);

    StatusDTO isLoginFree(String login);

    List<UserDTO> findUsersByLogin(String login);
    UserDTO getUser(User user);
}
