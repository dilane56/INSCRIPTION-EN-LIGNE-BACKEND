package org.kfokam48.inscriptionenlignebackend.service.impl;

import jakarta.transaction.Transactional;
import org.kfokam48.inscriptionenlignebackend.dto.admin.AdminRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.admin.AdminResponseDTO;
import org.kfokam48.inscriptionenlignebackend.exception.RessourceNotFoundException;
import org.kfokam48.inscriptionenlignebackend.mapper.AdminMapper;
import org.kfokam48.inscriptionenlignebackend.model.Admin;
import org.kfokam48.inscriptionenlignebackend.repository.AdminRepository;
import org.kfokam48.inscriptionenlignebackend.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper) {
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
        return adminMapper.adminToAdminResponseDTO(adminRepository.save(adminMapper.adminRequestDTOToAdmin(adminDTO)));
    }

    @Override
    public AdminResponseDTO updateAdmin(AdminRequestDTO adminDTO, Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("Admin not found"));
        admin.setEmail(adminDTO.getEmail());
        admin.setFirstName(adminDTO.getFirstName());
        admin.setLastName(adminDTO.getLastName());
        admin.setPassword(adminDTO.getPassword());

        return adminMapper.adminToAdminResponseDTO(adminRepository.save(admin));
    }

    @Override
    public String deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("Admin not found"));
        adminRepository.delete(admin);
        return "Admin deleted Successfully";
    }
}