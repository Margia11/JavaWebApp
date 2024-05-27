package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.CompanyDTO;
import it.plantict.officeolympics.dto.UserCompanyDTO;
import it.plantict.officeolympics.models.CompanyCreateRequest;
import it.plantict.officeolympics.models.UserCompanyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserCompanyService {

    List<UserCompanyDTO> getAllUserCompanies(int page, int size);
    long getCountAllUserCompanies();
    UserCompanyDTO getUserCompanyByIdCompany(UUID idCompany);
    UserCompanyDTO createUserCompany(UserCompanyRequest companyRequest);
    UserCompanyDTO updateUserCompany(UUID idCompany, UserCompanyRequest companyRequest);
    void deleteUserCompany(UUID idCompany);
}

