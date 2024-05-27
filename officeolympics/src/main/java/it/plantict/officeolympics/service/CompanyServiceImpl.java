package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.CompanyDTO;
import it.plantict.officeolympics.dto.GroupDTO;
import it.plantict.officeolympics.entities.CompanyEntity;
import it.plantict.officeolympics.entities.GroupEntity;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.mapper.CompanyMapper;
import it.plantict.officeolympics.mapper.GroupMapper;
import it.plantict.officeolympics.mapper.UserCompanyMapper;
import it.plantict.officeolympics.models.CompanyCreateRequest;
import it.plantict.officeolympics.models.CompanyRequest;
import it.plantict.officeolympics.repository.CompanyRepository;
import it.plantict.officeolympics.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService
{
    @Autowired
    private CompanyRepository companyRepository;

    private final Logger log = LogManager.getLogger(ExceptionHandler.class);

    private PageRequest createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

    @Override
    public List<CompanyDTO> getAllCompanies(int size, int page)
    {
        Pageable pageRequest = createPageRequestUsing(page, size);
        Page<CompanyEntity> allCompanies = companyRepository.findAll(pageRequest);
        List<CompanyDTO> contentDTO = CompanyMapper.companyMapper.mapToDTOs(allCompanies.toList());
        return contentDTO;
    }

    @Override
    public long getCountAllCompanies() {
        return companyRepository.count();
    }

    @Override
    public CompanyDTO getCompanyByVatNumber(String vatNumber) {
        CompanyEntity companyEntity = companyRepository.findByVatNumber(vatNumber).orElse(null);
        if(companyEntity == null)
           throw new EntityNotFoundException("Company not found");
        return CompanyMapper.companyMapper.toDTO(companyEntity);
    }

    //trasforma il dto in entity, la salva e poi viene ritrasformato in dto
    @Transactional //Esegue quando tutto è andato a buon fine
    @Override
    public CompanyDTO createCompany(CompanyCreateRequest companyRequest) throws MyDuplicateKeyException {

        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setName(companyRequest.getName());
        companyEntity.setDescription(companyRequest.getDescription());
        companyEntity.setLogo(companyRequest.getLogo());
        companyEntity.setLastUpdate(Utils.getCurrentTimestamp());
        if(!companyRepository.findByVatNumber(companyRequest.getVatNumber()).isEmpty())
            throw new MyDuplicateKeyException("VatNumber already exist");
        companyEntity.setVatNumber(companyRequest.getVatNumber());
        companyEntity = companyRepository.save(companyEntity);
        log.info("company created with UUID {}", companyEntity.getIdCompany());
        return CompanyMapper.companyMapper.toDTO(companyEntity);
    }

    @Override
    public CompanyDTO updateCompany(String vatNumber, CompanyRequest companyRequest) {
        CompanyEntity companyEntity = companyRepository.findByVatNumber(vatNumber).orElseThrow(() -> new EntityNotFoundException("Company not found"));
        if(companyRequest.getName() != null)
            companyEntity.setName(companyRequest.getName());
        if(companyRequest.getDescription() != null)
            companyEntity.setDescription(companyRequest.getDescription());
        if(companyRequest.getLogo() != null)
            companyEntity.setLogo(companyRequest.getLogo());
        companyEntity.setLastUpdate(Utils.getCurrentTimestamp());
        companyEntity  = companyRepository.save(companyEntity);
        log.info("company updated with UUID {}", companyEntity.getIdCompany());
        return CompanyMapper.companyMapper.toDTO(companyEntity);
    }

    @Transactional
    @Override
    public void deleteCompany(String vatNumber) {
        if(companyRepository.findByVatNumber(vatNumber).isEmpty())
            throw new EntityNotFoundException("Company doesn't exist");
        companyRepository.deleteByVatNumber(vatNumber);
        log.info("company deleted");
    }
}
