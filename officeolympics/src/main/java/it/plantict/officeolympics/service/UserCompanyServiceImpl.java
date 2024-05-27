package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.CompanyDTO;
import it.plantict.officeolympics.dto.UserCompanyDTO;
import it.plantict.officeolympics.entities.CompanyEntity;
import it.plantict.officeolympics.entities.UserCompanyEntity;
import it.plantict.officeolympics.entities.UserEntity;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.mapper.CompanyMapper;
import it.plantict.officeolympics.mapper.UserCompanyMapper;
import it.plantict.officeolympics.models.UserCompanyRequest;
import it.plantict.officeolympics.repository.CompanyRepository;
import it.plantict.officeolympics.repository.UserCompanyRepository;
import it.plantict.officeolympics.repository.UserRepository;
import it.plantict.officeolympics.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.UUID;

@Service
public class UserCompanyServiceImpl implements UserCompanyService{

    @Autowired
    private UserCompanyRepository userCompanyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private final Logger log = LogManager.getLogger(ExceptionHandler.class);

    private PageRequest createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

    @Override
    public List<UserCompanyDTO> getAllUserCompanies(int page, int size)
    {
        Pageable pageRequest = createPageRequestUsing(page, size);
        Page<UserCompanyEntity> allCompanies = userCompanyRepository.findAll(pageRequest);
        List<UserCompanyDTO> contentDTO = UserCompanyMapper.userCompanyMapper.mapToDTOs(allCompanies.toList());
        return contentDTO;
    }

    @Override
    public long getCountAllUserCompanies() {
        return userCompanyRepository.count();
    }

    @Override
    public UserCompanyDTO getUserCompanyByIdCompany(UUID idUserCompany) {
        UserCompanyEntity userCompanyEntity = userCompanyRepository.findByIdUserCompany(idUserCompany).orElseThrow(() -> new EntityNotFoundException("Company not found"));
        return UserCompanyMapper.userCompanyMapper.toDTO(userCompanyEntity);
    }

    @Transactional
    @Override
    public UserCompanyDTO createUserCompany(UserCompanyRequest companyRequest) {

        UserEntity userEntity = userRepository.findById(companyRequest.getIdUser()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        CompanyEntity companyEntity = companyRepository.findById(companyRequest.getIdCompany()).orElseThrow(() -> new EntityNotFoundException("Company not found"));
        UserCompanyEntity userCompanyEntity = new UserCompanyEntity();
        userCompanyEntity.setUser(userEntity);
        userCompanyEntity.setCompany(companyEntity);
        userCompanyEntity.setLastUpdate(Utils.getCurrentTimestamp());
        userCompanyEntity = userCompanyRepository.save(userCompanyEntity);
        log.info("company created with UUID {}", userCompanyEntity.getIdUserCompany());
        return UserCompanyMapper.userCompanyMapper.toDTO(userCompanyEntity);
    }

    @Override
    public UserCompanyDTO updateUserCompany(UUID idUserCompany, UserCompanyRequest companyRequest) {
        UserCompanyEntity userCompanyEntity = userCompanyRepository.findByIdUserCompany(idUserCompany).orElseThrow(() -> new EntityNotFoundException("Company not found"));
        UserEntity userEntity = userRepository.findById(companyRequest.getIdUser()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        CompanyEntity companyEntity = companyRepository.findById(companyRequest.getIdCompany()).orElseThrow(() -> new EntityNotFoundException("Company not found"));
        if(userCompanyEntity.getCompany() != null)
            userCompanyEntity.setCompany(companyEntity);
        if(userCompanyEntity.getUser() != null)
            userCompanyEntity.setUser(userEntity);
        userCompanyEntity.setLastUpdate(Utils.getCurrentTimestamp());
        userCompanyEntity  = userCompanyRepository.save(userCompanyEntity);
        log.info("company updated with UUID {}", userCompanyEntity.getIdUserCompany());
        return UserCompanyMapper.userCompanyMapper.toDTO(userCompanyEntity);
    }

    @Transactional
    @Override
    public void deleteUserCompany(UUID idCompany) {

        if(userCompanyRepository.findById(idCompany).isEmpty())
            throw new EntityNotFoundException("UserCompany doesn't exist");
        userCompanyRepository.deleteById(idCompany);
        log.info("company deleted");
    }
}
