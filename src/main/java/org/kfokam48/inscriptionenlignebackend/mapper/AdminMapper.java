package org.kfokam48.inscriptionenlignebackend.mapper;

import org.kfokam48.inscriptionenlignebackend.dto.admin.AdminRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.admin.AdminResponseDTO;
import org.kfokam48.inscriptionenlignebackend.model.Admin;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminMapper {
    private final ModelMapper modelMapper;

    public AdminMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public Admin adminRequestDTOToAdmin(AdminRequestDTO adminDTO) {
        return modelMapper.map(adminDTO, Admin.class);
    }
    public AdminResponseDTO adminToAdminResponseDTO(Admin admin) {
        return modelMapper.map(admin, AdminResponseDTO.class);
    }

    public List<AdminResponseDTO> adminListToAdminResponseDTOList(List<Admin> adminList) {
        return adminList.stream()
                .map(this::adminToAdminResponseDTO)
                .toList();
    }
}
