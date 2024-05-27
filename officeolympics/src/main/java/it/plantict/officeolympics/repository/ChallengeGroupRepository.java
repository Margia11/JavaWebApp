package it.plantict.officeolympics.repository;

import it.plantict.officeolympics.entities.ChallengeGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChallengeGroupRepository extends JpaRepository<ChallengeGroupEntity, UUID> {

    List<ChallengeGroupEntity> findAllByChallengeGroupIdGroup(UUID idGroup);


    List<ChallengeGroupEntity> findAllByChallengeIdChallenge(UUID idChallenge);
}
