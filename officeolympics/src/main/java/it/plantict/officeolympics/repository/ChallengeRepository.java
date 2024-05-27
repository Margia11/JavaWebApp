package it.plantict.officeolympics.repository;

import it.plantict.officeolympics.entities.ChallengeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeEntity, UUID> {

    Optional<ChallengeEntity> findByName(String name);

    List<ChallengeEntity> findAllByUserIdUser(UUID idUser);
}
