package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.user.UserResponseDTO;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.UserMapper;
import org.kfokam48.inscriptionenlignebackend.model.User;
import org.kfokam48.inscriptionenlignebackend.repository.UserRepository;
import org.kfokam48.inscriptionenlignebackend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userMapper.userListToUserResponseDTOList(userRepository.findAll());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user =userRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("User not found"));
        return userMapper.userToUserResponseDTO(user);
    }

    @Override
    public String deleteUserById(Long id) {
        User user =userRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("User not found"));
        userRepository.delete(user);
        return "User deleted Successfully";
    }
}
