package ru.b19513.pet_schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.InvitationDTO;
import ru.b19513.pet_schedule.controller.entity.UserDTO;
import ru.b19513.pet_schedule.repository.UserRepository;
import ru.b19513.pet_schedule.service.UserService;
import ru.b19513.pet_schedule.service.mapper.UserMapper;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO signInNewUser(String login, String pass, String name) {
        return null;
    }

    @Override
    public UserDTO updateUser(UserDTO user) {
        return null;
    }

    @Override
    public Collection<InvitationDTO> getInvitationByUserId(long id) {
        return null;
    }

    @Override
    public GroupDTO acceptInvintation(long userId, long groupId) {
        return null;
    }

    @Override
    public boolean isLoginFree(String login) {
        return false;
    }
}
