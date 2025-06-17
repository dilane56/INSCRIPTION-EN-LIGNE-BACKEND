package org.kfokam48.inscriptionenlignebackend.mapper;


import org.kfokam48.inscriptionenlignebackend.dto.user.UserRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.user.UserResponseDTO;
import org.kfokam48.inscriptionenlignebackend.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User userDTOToUser(UserRequestDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
    public UserResponseDTO userToUserResponseDTO(User user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public List<UserResponseDTO> userListToUserResponseDTOList(List<User> userList) {
        return modelMapper.map(userList, List.class);
    }
}
