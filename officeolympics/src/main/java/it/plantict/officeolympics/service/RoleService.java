package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.RoleDTO;
import it.plantict.officeolympics.models.RoleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    long getCountAllRoles();
    List<RoleDTO> getAllRoles(int page, int size);
    RoleDTO getRoleById(UUID id);
    RoleDTO createRole(RoleRequest roleRequest);
    RoleDTO updateRoleById(UUID id, RoleRequest roleRequest);
    void removeById(UUID id);
}
