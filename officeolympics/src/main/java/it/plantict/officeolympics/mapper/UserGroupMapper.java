package it.plantict.officeolympics.mapper;

import it.plantict.officeolympics.dto.UserGroupDTO;
import it.plantict.officeolympics.entities.UserGroupEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserGroupMapper {
    UserGroupMapper INSTANCE = Mappers.getMapper(UserGroupMapper.class);

    @Named("toEntity")
    UserGroupEntity toEntity(UserGroupDTO dto);

    @Named("toDTO")
    UserGroupDTO toDTO(UserGroupEntity entity);

    @IterableMapping(qualifiedByName = "toDTO")
    List<UserGroupDTO> mapToDTOs(List<UserGroupEntity> entities);

    @IterableMapping(qualifiedByName = "toEntity")
    List<UserGroupEntity> mapToEntities(List<UserGroupDTO> dtos);
}
