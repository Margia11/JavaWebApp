package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.ChallengeGroupDTO;
import it.plantict.officeolympics.entities.ChallengeEntity;
import it.plantict.officeolympics.entities.ChallengeGroupEntity;
import it.plantict.officeolympics.entities.GroupEntity;
import it.plantict.officeolympics.mapper.ChallengeGroupMapper;
import it.plantict.officeolympics.models.ChallengeGroupRequest;
import it.plantict.officeolympics.repository.ChallengeGroupRepository;
import it.plantict.officeolympics.repository.ChallengeRepository;
import it.plantict.officeolympics.repository.GroupRepository;
import it.plantict.officeolympics.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ChallengeGroupServiceImpl implements ChallengeGroupService {

    @Autowired
    private ChallengeGroupRepository    repository;

    @Autowired
    private ChallengeRepository         challengeRepository;

    @Autowired
    private GroupRepository groupRepository;

    private final Logger logger = LogManager.getLogger(ChallengeGroupServiceImpl.class);

    public long totalChallengeGroup() {
        return repository.count();
    }

    @Override
    public List<ChallengeGroupDTO> getAllChallengeGroups(int page, int size) {
        logger.info("asked pagination of page {} and size {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<ChallengeGroupEntity> challengeGroupEntities = repository.findAll(pageable);
        return ChallengeGroupMapper.INSTANCE.toDTOList(challengeGroupEntities.toList());
    }

    @Override
    public ChallengeGroupDTO getChallengeGroupById(UUID id) {
        ChallengeGroupEntity challengeGroup = repository.findById(id).orElse(null);
        if (challengeGroup == null) {
            throw new EntityNotFoundException("no challengeGroup found with the specified id");
        }
        return ChallengeGroupMapper.INSTANCE.toDTO(challengeGroup);
    }

    @Override
    public List<ChallengeGroupDTO> getAllByGroup(UUID idGroup) {
        GroupEntity group = groupRepository.findById(idGroup).orElseThrow(() -> new EntityNotFoundException("group specified not found"));
        List<ChallengeGroupEntity> challengeGroupEntities = repository.findAllByChallengeGroupIdGroup(idGroup);
        return ChallengeGroupMapper.INSTANCE.toDTOList(challengeGroupEntities);
    }

    @Override
    public List<ChallengeGroupDTO> getAllByChallenge(UUID idChallenge) {
        ChallengeEntity challenge = challengeRepository.findById(idChallenge).orElseThrow(() -> new EntityNotFoundException("challenge specified not found"));
        List<ChallengeGroupEntity> challengeGroupEntities = repository.findAllByChallengeIdChallenge(idChallenge);
        return ChallengeGroupMapper.INSTANCE.toDTOList(challengeGroupEntities);
    }

    @Override
    @Transactional
    public ChallengeGroupDTO createChallengeGroup(ChallengeGroupRequest request) {
        ChallengeGroupEntity challengeGroup = new ChallengeGroupEntity();
        ChallengeEntity challenge = challengeRepository.findById(request.getIdChallenge()).orElseThrow(() -> new EntityNotFoundException("idChallenge specified does not exist."));
        GroupEntity group = groupRepository.findById(request.getIdGroup()).orElseThrow(() -> new EntityNotFoundException("idGroup specified does not exist."));
        challengeGroup.setChallenge(challenge);
        challengeGroup.setChallengeGroup(group);
        if (request.getStartDate() == null) {
            throw new IllegalArgumentException("starting date must be specified.");
        }
        challengeGroup.setStartDate(request.getStartDate());
        if (request.getEndDate() == null) {
            throw new IllegalArgumentException("ending date must be specified");
        }
        LocalDateTime startDate = request.getStartDate().toLocalDateTime();
        LocalDateTime endDate = request.getEndDate().toLocalDateTime();
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("ending date must be after starting date");
        }
        challengeGroup.setEndDate(request.getEndDate());
        challengeGroup.setLastUpdate(Utils.getCurrentTimestamp());
        challengeGroup = repository.save(challengeGroup);
        logger.info("challengeGroup with id {} created and assigned to group {}", challengeGroup.getIdChallengeGroup(), challengeGroup.getChallengeGroup());
        return ChallengeGroupMapper.INSTANCE.toDTO(challengeGroup);
    }

    @Override
    @Transactional
    public ChallengeGroupDTO updateChallengeGroup(UUID id, ChallengeGroupRequest request) {
        ChallengeGroupEntity challengeGroup = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("idChallengeGroup specified does not exist"));
        logger.info("updating challengeGroup with id {}...", challengeGroup.getIdChallengeGroup());
        ChallengeEntity challenge = challengeRepository.findById(request.getIdChallenge()).orElseThrow(() -> new EntityNotFoundException("idChallenge specified does not exist"));
        GroupEntity group = groupRepository.findById(request.getIdGroup()).orElseThrow(() -> new EntityNotFoundException("idGroup specified does not exist."));
        challengeGroup.setChallenge(challenge);
        challengeGroup.setChallengeGroup(group);
        if (request.getEndDate() != null) {
            challengeGroup.setEndDate(request.getEndDate());
        }
        if (request.getStartDate() != null) {
            challengeGroup.setStartDate(request.getStartDate());
        }
        challengeGroup.setLastUpdate(Utils.getCurrentTimestamp());
        LocalDateTime startDate = challengeGroup.getStartDate().toLocalDateTime();
        LocalDateTime endDate = challengeGroup.getEndDate().toLocalDateTime();
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("start date must be before end date");
        }
        challengeGroup = repository.save(challengeGroup);
        logger.info("update completed successfully");
        return ChallengeGroupMapper.INSTANCE.toDTO(challengeGroup);
    }

    @Override
    public void deleteChallengeGroup(UUID id) {
        if (repository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("no challengeGroup found with specified id.");
        }
        repository.deleteById(id);
    }
}
