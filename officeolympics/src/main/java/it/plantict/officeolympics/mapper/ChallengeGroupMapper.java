package it.plantict.officeolympics.mapper;

import it.plantict.officeolympics.dto.ChallengeGroupDTO;
import it.plantict.officeolympics.entities.ChallengeGroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ChallengeGroupMapper {

    ChallengeGroupMapper INSTANCE = Mappers.getMapper(ChallengeGroupMapper.class);

    ChallengeGroupEntity toEntity(ChallengeGroupDTO dto);

    ChallengeGroupDTO toDTO(ChallengeGroupEntity entity);

    List<ChallengeGroupEntity> toEntityList(List<ChallengeGroupDTO> dtos);

    List<ChallengeGroupDTO> toDTOList(List<ChallengeGroupEntity> entities);
}

