package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.CompanyDTO;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.models.CompanyCreateRequest;
import it.plantict.officeolympics.models.CompanyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService
{
    List<CompanyDTO> getAllCompanies(int page, int size);
    long getCountAllCompanies();
    CompanyDTO getCompanyByVatNumber(String vatNumber);
    CompanyDTO createCompany(CompanyCreateRequest companyRequest) throws MyDuplicateKeyException;
    CompanyDTO updateCompany(String vatNumber, CompanyRequest companyRequest);
    void deleteCompany(String vatNumber);
}
