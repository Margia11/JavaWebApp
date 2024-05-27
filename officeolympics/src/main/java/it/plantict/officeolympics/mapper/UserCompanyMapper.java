package it.plantict.officeolympics.mapper;

import it.plantict.officeolympics.dto.CompanyDTO;
import it.plantict.officeolympics.dto.UserCompanyDTO;
import it.plantict.officeolympics.entities.CompanyEntity;
import it.plantict.officeolympics.entities.UserCompanyEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserCompanyMapper {

    UserCompanyMapper userCompanyMapper = Mappers.getMapper(UserCompanyMapper.class);
    @Named("toDTO")
    UserCompanyDTO toDTO(UserCompanyEntity entity);
    @Named("toEntity")
    UserCompanyEntity toEntity(UserCompanyDTO dto);
    @IterableMapping(qualifiedByName = "toDTO")
    List<UserCompanyDTO> mapToDTOs(List<UserCompanyEntity> entities);
}
