package it.plantict.officeolympics.mapper;

import it.plantict.officeolympics.dto.RoleDTO;
import it.plantict.officeolympics.dto.UserDTO;
import it.plantict.officeolympics.entities.RoleEntity;
import it.plantict.officeolympics.entities.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);
    @Named("toModel")
    RoleEntity toEntity(RoleDTO dto);

    @Named("toDTO")
    RoleDTO toDTO(RoleEntity entity);

    @IterableMapping(qualifiedByName = "toDTO")
    List<RoleDTO> mapToDTOs(List<RoleEntity> entities);

    @IterableMapping(qualifiedByName = "toModel")
    List<RoleEntity> mapToEntities(List<RoleDTO> dtos);
}
