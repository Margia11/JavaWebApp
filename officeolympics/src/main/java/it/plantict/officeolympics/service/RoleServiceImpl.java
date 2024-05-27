package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.RoleDTO;
import it.plantict.officeolympics.entities.RoleEntity;
import it.plantict.officeolympics.mapper.RoleMapper;
import it.plantict.officeolympics.models.RoleRequest;
import it.plantict.officeolympics.repository.RoleRepository;
import it.plantict.officeolympics.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    private final Logger logger = LogManager.getLogger(ChallengeServiceImpl.class);


    @Override
    public long getCountAllRoles() {
        return roleRepository.count();
    }

    @Override
    public List<RoleDTO> getAllRoles(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<RoleEntity> roles = roleRepository.findAll(pageRequest);
        return RoleMapper.MAPPER.mapToDTOs(roles.stream().toList());
    }

    @Override
    public RoleDTO getRoleById(UUID id) {
        RoleEntity roleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Role with id " + id + " not found")));
        return RoleMapper.MAPPER.toDTO(roleEntity);
    }

    @Transactional
    @Override
    public RoleDTO createRole(RoleRequest roleRequest) {
        if (roleRequest.getName() == null || roleRequest.getName().isEmpty()) {
            logger.error("roleName cannot be null or empty");
            throw new IllegalArgumentException("roleName cannot be null or empty");
        }
        if (roleRequest.getDescription() == null || roleRequest.getDescription().isEmpty()) {
            logger.error("roleDescription cannot be null or empty");
            throw new IllegalArgumentException("roleDescription cannot be null or empty");
        }
        RoleEntity newRoleEntity = new RoleEntity();
        newRoleEntity.setName(roleRequest.getName());
        newRoleEntity.setDescription(roleRequest.getDescription());
        newRoleEntity.setLastUpdate(Utils.getCurrentTimestamp());
        roleRepository.save(newRoleEntity);
        logger.info("Role created with id {}", newRoleEntity.getIdRole());
        return RoleMapper.MAPPER.toDTO(newRoleEntity);
    }

    @Transactional
    @Override
    public RoleDTO updateRoleById(UUID id, RoleRequest roleRequest) {
        RoleEntity roleToUpdate = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
        if (roleRequest.getName() != null && !roleRequest.getName().isEmpty())
            roleToUpdate.setName(roleRequest.getName());
        if (roleRequest.getDescription() != null && !roleRequest.getDescription().isEmpty())
            roleToUpdate.setDescription(roleRequest.getDescription());
        roleToUpdate.setLastUpdate(Utils.getCurrentTimestamp());
        return RoleMapper.MAPPER.toDTO(roleToUpdate);
    }

    @Transactional
    @Override
    public void removeById(UUID id) {
        Optional<RoleEntity> roleToCheck = roleRepository.findById(id);
        if (roleToCheck.isEmpty()) {
            logger.error("No Role found with id: {}", id);
            throw new EntityNotFoundException("No role found with username: " + id);
        }
        roleRepository.deleteById(id);
    }
}
