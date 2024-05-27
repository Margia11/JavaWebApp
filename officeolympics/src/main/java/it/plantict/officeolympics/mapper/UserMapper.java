package it.plantict.officeolympics.mapper;

import java.util.List;

import it.plantict.officeolympics.dto.UserDTO;
import it.plantict.officeolympics.entities.UserEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Named("toModel")
    UserEntity toEntity(UserDTO dto);

    @Named("toDTO")
    UserDTO toDTO(UserEntity entity);

    @IterableMapping(qualifiedByName = "toDTO")
    List<UserDTO> mapToDTOs(List<UserEntity> entities);

    @IterableMapping(qualifiedByName = "toModel")
    List<UserEntity> mapToEntities(List<UserDTO> dtos);

/*
    default RoleEntity roleDTOtoRoleEntity(RoleDTO roleDTO) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setIdRole(roleDTO.getIdRole());
        return roleEntity;
    }
*/

/*    default RoleDTO roleEntityToRoleDTO(RoleEntity roleEntity) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setIdRole(roleEntity.getIdRole());
        return roleDTO;
    }*/
}
