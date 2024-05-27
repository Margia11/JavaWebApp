package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.GroupDTO;
import it.plantict.officeolympics.entities.CompanyEntity;
import it.plantict.officeolympics.entities.GroupEntity;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.mapper.GroupMapper;
import it.plantict.officeolympics.models.GroupRequest;
import it.plantict.officeolympics.repository.GroupRepository;
import it.plantict.officeolympics.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    @Transactional
    public GroupDTO createGroup(GroupRequest groupRequest) throws MyDuplicateKeyException {
        if (groupRequest.getName() == null) {
            log.error("Cannot create new Group with null Name");
            throw new IllegalArgumentException("Name must not be null");
        } else if (groupRequest.getType() == null) {
            log.error("Cannot create new Group with null Type");
            throw new IllegalArgumentException("Type must not be null");
        } else if (groupRequest.getDescription() == null) {
            log.error("Cannot create new Group with null Description");
            throw new IllegalArgumentException("Description must not be null");
        } else if (groupRequest.getIdCompany() == null) {
            log.error("Cannot create new Group with null IdCompany");
            throw new IllegalArgumentException("IdCompany must not be null");
        }
        else {
            CompanyEntity companyEntity = companyRepository.findById(groupRequest.getIdCompany()).orElseThrow(() -> new EntityNotFoundException("CompanyEntity not found"));
            GroupEntity groupEntity = new GroupEntity();

            if (groupRepository.findByName(groupRequest.getName()).isPresent()) {
                log.error("Cannot create new Group with already existing name");
                throw new MyDuplicateKeyException(groupRequest.getName() + " as group name already exists");
            }

            groupEntity.setCompany(companyEntity);
            groupEntity.setName(groupRequest.getName());
            groupEntity.setType(groupRequest.getType());
            groupEntity.setDescription(groupRequest.getDescription());

            groupEntity = groupRepository.save(groupEntity);

            log.info("Made a post request for creating a new group with ID_group: {}", groupEntity.getIdGroup());
            return GroupMapper.INSTANCE.toDTO(groupEntity);
        }
    }

    @Override
    public GroupDTO updateGroup(UUID idGroup, GroupDTO groupDTO) {
        GroupEntity groupEntity = groupRepository.findById(idGroup).orElseThrow(() -> new EntityNotFoundException("Group not found"));
        CompanyEntity companyEntity = companyRepository.findById(groupDTO.getCompany().getIdCompany()).orElseThrow(() -> new EntityNotFoundException("Company not found"));

        groupEntity.setIdGroup(groupDTO.getIdGroup());
        groupEntity.setCompany(companyEntity);
        groupEntity.setType(groupDTO.getType());
        groupEntity.setName(groupDTO.getName());
        groupEntity.setDescription(groupDTO.getDescription());

        log.info("Made a put request for updating an existing group with ID_group: {}", groupEntity.getIdGroup());

        GroupEntity updatedGroupEntity = groupRepository.save(groupEntity);
        return GroupMapper.INSTANCE.toDTO(updatedGroupEntity);
    }

    @Override
    public GroupDTO getGroupById(UUID idGroup) {
        GroupEntity groupEntity = groupRepository.findById(idGroup).orElseThrow(() -> new EntityNotFoundException("Group not found"));

        log.info("Made a getById request for ID {} group", groupEntity.getIdGroup());
        return GroupMapper.INSTANCE.toDTO(groupEntity);
    }

    private PageRequest createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }
    @Override
    public  List<GroupDTO> getAllGroups(int page, int size) {
        Pageable pageRequest = createPageRequestUsing(page, size);
        Page<GroupEntity> allGroups = groupRepository.findAll(pageRequest);
        return GroupMapper.INSTANCE.mapToDTOs(allGroups.toList());
    }

    @Override
    public long getCountAllGroups()
    {
        return groupRepository.count();
    }

    @Override
    public void deleteGroup(UUID idGroup) {
        GroupEntity groupEntity = groupRepository.findById(idGroup).orElseThrow(() -> new EntityNotFoundException("Group not found"));

        log.info("Made a delete request for removing the existing group with ID_group: {}", idGroup);
        groupRepository.deleteById(idGroup);
    }
}
