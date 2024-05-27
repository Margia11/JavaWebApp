package it.plantict.officeolympics.repository;

import it.plantict.officeolympics.entities.UserCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCompanyRepository extends JpaRepository<UserCompanyEntity, UUID> {

    Optional<UserCompanyEntity> findByIdUserCompany(UUID idUserCompany);
    void deleteById(UUID idCompany);
}
