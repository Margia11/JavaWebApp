package it.plantict.officeolympics.mapper;

import it.plantict.officeolympics.dto.GroupDTO;
import it.plantict.officeolympics.entities.GroupEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    @Named("toEntity")
    GroupEntity toEntity(GroupDTO dto);

    @Named("toDTO")
    GroupDTO toDTO(GroupEntity entity);

    @IterableMapping(qualifiedByName = "toDTO")
    List<GroupDTO> mapToDTOs(List<GroupEntity> entities);

    @IterableMapping(qualifiedByName = "toEntity")
    List<GroupEntity> mapToEntities(List<GroupDTO> dtos);
}
