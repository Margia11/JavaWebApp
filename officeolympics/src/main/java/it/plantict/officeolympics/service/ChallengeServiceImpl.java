package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.ChallengeDTO;
import it.plantict.officeolympics.entities.ChallengeEntity;
import it.plantict.officeolympics.entities.UserEntity;
import it.plantict.officeolympics.exceptions.ExceptionHandler;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.mapper.ChallengeMapper;
import it.plantict.officeolympics.models.ChallengeRequest;
import it.plantict.officeolympics.repository.ChallengeRepository;
import it.plantict.officeolympics.repository.UserRepository;
import it.plantict.officeolympics.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LogManager.getLogger(ExceptionHandler.class);

    @Override
    public ChallengeDTO getChallengeByName(String name) {
        ChallengeEntity challenge = challengeRepository.findByName(name).orElse(null);
        if (challenge == null) {
            throw new EntityNotFoundException("no challenge found with specified name.");
        }
        return ChallengeMapper.INSTANCE.toDTO(challenge);
    }

    public long totalChallenge() {
        return challengeRepository.count();
    }

    @Override
    public List<ChallengeDTO> getAllChallenges(int page, int size) {
        logger.info("asked pagination of page {} and size {} ", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<ChallengeEntity> challengeEntities = challengeRepository.findAll(pageable);
        return ChallengeMapper.INSTANCE.toDTOList(challengeEntities.toList());
    }

    @Override
    public List<ChallengeDTO> getAllByUser(UUID idUser) {
        UserEntity user = userRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("user specified not found"));
        List<ChallengeEntity> challengeEntities = challengeRepository.findAllByUserIdUser(idUser);
        return ChallengeMapper.INSTANCE.toDTOList(challengeEntities);
    }

    @Override
    @Transactional
    public ChallengeDTO updateChallenge(UUID idChallenge, ChallengeRequest request) {
        ChallengeEntity challengeEntity = challengeRepository.findById(idChallenge).orElseThrow(() -> new EntityNotFoundException("challenge specified not found"));
        logger.info("updating challenge with name {}...", challengeEntity.getName());
        UserEntity userEntity = userRepository.findById(request.getIdUser()).orElseThrow(() -> new EntityNotFoundException("user specified not found"));
        challengeEntity.setUser(userEntity);
        if (request.getName() != null && !request.getName().isBlank()) {
            challengeEntity.setName(request.getName());
        }
        if (request.getDescription() != null) {
            challengeEntity.setDescription(request.getDescription());
        }
        challengeEntity.setLastUpdate(Utils.getCurrentTimestamp());
        challengeEntity = challengeRepository.save(challengeEntity);
        logger.info("update completed successfully");
        return ChallengeMapper.INSTANCE.toDTO(challengeEntity);
    }

    @Override
    @Transactional
    public ChallengeDTO createChallenge(ChallengeRequest request) throws MyDuplicateKeyException {
        ChallengeEntity challengeEntity = new ChallengeEntity();
        UserEntity userEntity = userRepository.findById(request.getIdUser()).orElseThrow(() -> new EntityNotFoundException("user specified not found"));
        challengeEntity.setUser(userEntity);
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("name must be specified");
        }
        if (challengeRepository.findByName(request.getName()).isPresent()) {
            throw new MyDuplicateKeyException("challenge with name specified already exists");
        }
        challengeEntity.setName(request.getName());
        challengeEntity.setDescription(request.getDescription());
        challengeEntity.setLastUpdate(Utils.getCurrentTimestamp());
        challengeEntity = challengeRepository.save(challengeEntity);
        logger.info("challenge created with UUID {} and assigned to user {}", challengeEntity.getIdChallenge(), challengeEntity.getUser().getFirstName());
        return ChallengeMapper.INSTANCE.toDTO(challengeEntity);
    }

    @Override
    public void destroyChallenge(UUID idChallenge) {
        if (challengeRepository.findById(idChallenge).isEmpty()) {
            throw new EntityNotFoundException("challenge with specified id does not exist.");
        }
        challengeRepository.deleteById(idChallenge);
    }
}
