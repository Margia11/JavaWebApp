package it.plantict.officeolympics.mapper;

import it.plantict.officeolympics.dto.CompanyDTO;
import it.plantict.officeolympics.dto.RoleDTO;
import it.plantict.officeolympics.entities.CompanyEntity;
import it.plantict.officeolympics.entities.RoleEntity;
import it.plantict.officeolympics.models.CompanyRequest;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper
{
    CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);
    @Named("toDTO")
    CompanyDTO toDTO(CompanyEntity entity);
    @Named("toEntity")
    CompanyEntity toEntity(CompanyDTO dto);
    @IterableMapping(qualifiedByName = "toDTO")
    List<CompanyDTO> mapToDTOs(List<CompanyEntity> entities);

}
