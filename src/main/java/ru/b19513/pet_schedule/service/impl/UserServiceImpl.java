package ru.b19513.pet_schedule.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.b19513.pet_schedule.controller.entity.GroupDTO;
import ru.b19513.pet_schedule.controller.entity.InvitationDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.controller.entity.UserDTO;
import ru.b19513.pet_schedule.exceptions.LoginBusyException;
import ru.b19513.pet_schedule.exceptions.NotFoundException;
import ru.b19513.pet_schedule.repository.InvitationRepository;
import ru.b19513.pet_schedule.repository.UserRepository;
import ru.b19513.pet_schedule.repository.entity.Invitation;
import ru.b19513.pet_schedule.repository.entity.User;
import ru.b19513.pet_schedule.service.UserService;
import ru.b19513.pet_schedule.service.mapper.EnumMapper;
import ru.b19513.pet_schedule.service.mapper.GroupMapper;
import ru.b19513.pet_schedule.service.mapper.InvitationMapper;
import ru.b19513.pet_schedule.service.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final InvitationMapper invitationMapper;
    private final GroupMapper groupMapper;
    private final EnumMapper enumMapper;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, InvitationMapper invitationMapper, GroupMapper groupMapper,
                           EnumMapper enumMapper, UserRepository userRepository, InvitationRepository invitationRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userMapper = userMapper;
        this.invitationMapper = invitationMapper;
        this.groupMapper = groupMapper;
        this.enumMapper = enumMapper;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDTO signInNewUser(String login, String pass, String name) {
        if (userRepository.existsByLogin(login)) {
            throw new LoginBusyException("Login \"" + login + "\" already exist");
        }
        var user = User.builder()
                .login(login)
                .name(name)
                .password(bCryptPasswordEncoder.encode(pass))
                .build();
        return userMapper.entityToDTO(userRepository.save(user));

    }

    @Override
    public UserDTO updateUser(User user, UserDTO userDTO) {
        if (userDTO.getAbout() != null) {
            user.setAbout(userDTO.getAbout());
        }
        if (userDTO.getGender() != null) {
            user.setGender(enumMapper.DTOtoEntity(userDTO.getGender()));
        }
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        return userMapper.entityToDTO(userRepository.save(user));
    }

    @Override
    public Collection<InvitationDTO> getInvitation(User user) {
        return invitationMapper.entityToDTO(user.getInvitations());
    }

    @Override
    public GroupDTO acceptInvintation(User user, long groupId) {
        var invitation = invitationRepository.findById(new Invitation.Key(user.getId(), groupId))
                .orElseThrow(new NotFoundException("Invitation with user id: " + user.getId() + " and group id: " + groupId + "not found"));
        var group = invitation.getGroup();
        user.getGroups().add(group);
        userRepository.save(user);
        invitationRepository.delete(invitation);
        return groupMapper.entityToDTO(group);
    }

    @Override
    public StatusDTO isLoginFree(String login) {
        return StatusDTO.builder()
                .status(HttpStatus.OK)
                .description(userRepository.existsByLogin(login) ? "true" : "false")
                .build();
    }

    @Override
    public List<UserDTO> findUsersByLogin(String login) {
        return userMapper.entityToDTOConf(userRepository.findTop5ByLoginIsStartingWith(login));
    }

    @Override
    public UserDTO getUser(User user) {
        return userMapper.entityToDTO(user);
    }
}
