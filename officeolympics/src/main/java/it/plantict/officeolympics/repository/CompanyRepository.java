package it.plantict.officeolympics.repository;

import it.plantict.officeolympics.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    //optional consente di avere un ritorno nullo nel caso in cui non venga trovata alcuna azienda
    Optional<CompanyEntity> findByVatNumber(String vatNumber);
    void deleteByVatNumber(String vatNumber);
}
