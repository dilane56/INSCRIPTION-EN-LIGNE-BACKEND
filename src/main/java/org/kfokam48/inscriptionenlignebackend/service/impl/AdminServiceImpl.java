package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.admin.AdminRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.admin.AdminResponseDTO;
import org.kfokam48.inscriptionenlignebackend.enums.Roles;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceAlreadyExistException;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.AdminMapper;
import org.kfokam48.inscriptionenlignebackend.model.Admin;
import org.kfokam48.inscriptionenlignebackend.repository.AdminRepository;
import org.kfokam48.inscriptionenlignebackend.repository.UserRepository;
import org.kfokam48.inscriptionenlignebackend.service.AdminService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public AdminServiceImpl(UserRepository userRepository, AdminRepository adminRepository, AdminMapper adminMapper) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }

    @Override
    public List<AdminResponseDTO> getAllAdmins() {
        return adminMapper.adminListToAdminResponseDTOList(adminRepository.findAll());
    }

    @Override
    public AdminResponseDTO getAdminById(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("Admin not found"));
        return adminMapper.adminToAdminResponseDTO(admin);
    }

    @Override
    public AdminResponseDTO createAdmin(AdminRequestDTO adminDTO) {
        if(userRepository.existsByEmail(adminDTO.getEmail())){
            throw new RessourceAlreadyExistException("Email already exists");
        }else {
            Admin admin = adminMapper.adminRequestDTOToAdmin(adminDTO);
            admin.setRole(Roles.ADMIN);
            admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
            return adminMapper.adminToAdminResponseDTO(adminRepository.save(admin));
        }

    }

    @Override
    public AdminResponseDTO updateAdmin(AdminRequestDTO adminDTO, Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("Admin not found"));
        if(userRepository.existsByEmail(adminDTO.getEmail())&& admin.getEmail().equals(adminDTO.getEmail())){
            admin.setEmail(adminDTO.getEmail());
            admin.setFirstName(adminDTO.getFirstName());
            admin.setLastName(adminDTO.getLastName());
           admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
            admin.setRole(Roles.ADMIN);
            return adminMapper.adminToAdminResponseDTO(adminRepository.save(admin));

        }else{
            throw new RessourceAlreadyExistException("Email already exists");
        }


    }

    @Override
    public String deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("Admin not found"));
        adminRepository.delete(admin);
        return "Admin deleted Successfully";
    }
}