package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.ChallengeDTO;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.models.ChallengeRequest;

import java.util.List;
import java.util.UUID;

public interface ChallengeService {

    ChallengeDTO        getChallengeByName(String name);

    List<ChallengeDTO>  getAllChallenges(int page, int size);

    List<ChallengeDTO>  getAllByUser(UUID idUser);

    ChallengeDTO        updateChallenge(UUID idChallenge, ChallengeRequest request);

    ChallengeDTO        createChallenge(ChallengeRequest request) throws MyDuplicateKeyException;

    void                destroyChallenge(UUID idChallenge);

    long                totalChallenge();
}
