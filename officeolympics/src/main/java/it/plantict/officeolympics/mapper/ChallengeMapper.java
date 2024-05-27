package it.plantict.officeolympics.mapper;

import it.plantict.officeolympics.dto.ChallengeDTO;
import it.plantict.officeolympics.entities.ChallengeEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {

    ChallengeMapper INSTANCE = Mappers.getMapper(ChallengeMapper.class);

    @Named("toEntity")
    ChallengeEntity toEntity(ChallengeDTO dto);

    @Named("toDTO")
    ChallengeDTO toDTO(ChallengeEntity entity);

    @IterableMapping(qualifiedByName = "toEntity")
    List<ChallengeEntity> toEntityList(List<ChallengeDTO> dtos);

    @IterableMapping(qualifiedByName = "toDTO")
    List<ChallengeDTO> toDTOList(List<ChallengeEntity> entities);

    /*
    default UserEntity userDTOToUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(userDTO.getIdUser());
        return userEntity;
    }

    default UserDTO userEntityToUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(userEntity.getIdUser());
        return userDTO;
    }
     */
}
