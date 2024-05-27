package it.plantict.officeolympics.repository;

import it.plantict.officeolympics.entities.ChallengeEntity;
import it.plantict.officeolympics.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {

    Optional<GroupEntity> findByName(String name);

}
