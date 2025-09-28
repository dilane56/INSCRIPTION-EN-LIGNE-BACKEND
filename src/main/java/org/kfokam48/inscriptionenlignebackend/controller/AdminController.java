package org.kfokam48.inscriptionenlignebackend.controller;

import org.kfokam48.inscriptionenlignebackend.dto.admin.AdminRequestDTO;
import org.kfokam48.inscriptionenlignebackend.dto.admin.AdminResponseDTO;
import org.kfokam48.inscriptionenlignebackend.service.impl.AdminServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminServiceImpl adminService;

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }


    @GetMapping
    public List<AdminResponseDTO> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public AdminResponseDTO getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    @PostMapping
    public AdminResponseDTO createAdmin(@RequestBody AdminRequestDTO dto) {
        return adminService.createAdmin(dto);
    }

    @PutMapping("/{id}")
    public AdminResponseDTO updateAdmin(@RequestBody AdminRequestDTO dto, @PathVariable Long id) {
        return adminService.updateAdmin(dto, id);
    }

    @DeleteMapping("/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        return adminService.deleteAdmin(id);
    }


}
