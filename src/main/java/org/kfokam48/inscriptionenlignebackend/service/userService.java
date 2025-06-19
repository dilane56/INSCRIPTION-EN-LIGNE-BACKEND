package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.user.UserResponseDTO;

import java.util.List;

public interface userService {
    public List<UserResponseDTO> getAllUsers();
    public UserResponseDTO getUserById(Long id);
    public String deleteUserById(Long id);
}
