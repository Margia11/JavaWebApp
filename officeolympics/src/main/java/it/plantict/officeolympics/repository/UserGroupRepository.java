package it.plantict.officeolympics.repository;

import it.plantict.officeolympics.entities.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroupEntity, UUID> {
}
