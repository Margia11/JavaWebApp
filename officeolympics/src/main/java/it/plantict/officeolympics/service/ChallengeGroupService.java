package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.ChallengeGroupDTO;
import it.plantict.officeolympics.models.ChallengeGroupRequest;

import java.util.List;
import java.util.UUID;

public interface ChallengeGroupService {

    List<ChallengeGroupDTO> getAllChallengeGroups(int page, int size);

    ChallengeGroupDTO       getChallengeGroupById(UUID id);

    List<ChallengeGroupDTO> getAllByGroup(UUID idGroup);

    List<ChallengeGroupDTO> getAllByChallenge(UUID idChallenge);

    ChallengeGroupDTO       createChallengeGroup(ChallengeGroupRequest request);

    ChallengeGroupDTO       updateChallengeGroup(UUID id, ChallengeGroupRequest request);

    void                    deleteChallengeGroup(UUID id);

    long                    totalChallengeGroup();
}
