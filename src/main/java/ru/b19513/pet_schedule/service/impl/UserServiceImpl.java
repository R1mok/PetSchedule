package ru.b19513.pet_schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final InvitationMapper invitationMapper;
    private final GroupMapper groupMapper;
    private final EnumMapper enumMapper;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    private final MessageDigest digest;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, InvitationMapper invitationMapper, GroupMapper groupMapper, EnumMapper enumMapper, UserRepository userRepository, InvitationRepository invitationRepository) throws NoSuchAlgorithmException {
        this.userMapper = userMapper;
        this.invitationMapper = invitationMapper;
        this.groupMapper = groupMapper;
        this.enumMapper = enumMapper;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        digest = MessageDigest.getInstance("SHA-256");
    }

    @Override
    public UserDTO signInNewUser(String login, String pass, String name) {
        if (userRepository.existsByLogin(login))
            throw new LoginBusyException();
        var user = User.builder()
                .login(login)
                .name(name)
                .passwordHash(digest.digest(pass.getBytes(StandardCharsets.UTF_8)))
                .build();
        return userMapper.entityToDTO(userRepository.save(user));

    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        var user = userRepository.findById(userDTO.getId()).orElseThrow(NotFoundException::new);
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
    public Collection<InvitationDTO> getInvitationByUserId(long id) {
        var user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        return invitationMapper.entityToDTO(user.getInvitations());
    }

    @Override
    public GroupDTO acceptInvintation(long userId, long groupId) {
        var invitation = invitationRepository.findById(new Invitation.Key(userId, groupId)).orElseThrow(NotFoundException::new);
        var user = invitation.getUser();
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
    public UserDTO getUser(long id) {
        return userMapper.entityToDTO(userRepository.findById(id).orElseThrow(NotFoundException::new));
    }
}
