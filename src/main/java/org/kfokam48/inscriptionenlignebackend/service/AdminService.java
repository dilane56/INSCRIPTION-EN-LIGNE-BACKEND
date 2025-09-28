package org.kfokam48.inscriptionenlignebackend.service;

import org.kfokam48.inscriptionenlignebackend.dto.admin.AdminRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.admin.AdminResponseDTO;

import java.util.List;

public interface AdminService {
    public List<AdminResponseDTO> getAllAdmins();
    public AdminResponseDTO getAdminById(Long id);
    public AdminResponseDTO createAdmin(AdminRequestDTO adminDTO);
    public AdminResponseDTO updateAdmin(AdminRequestDTO adminDTO, Long id);
    public String deleteAdmin(Long id);
    public List<org.kfokam48.inscriptionenlignebackend.dto.candidat.CandidatResponseDTO> getAllCandidatsForAdmin();
}
