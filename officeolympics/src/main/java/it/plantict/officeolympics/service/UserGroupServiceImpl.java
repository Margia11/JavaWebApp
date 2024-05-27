package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.GroupDTO;
import it.plantict.officeolympics.dto.UserGroupDTO;
import it.plantict.officeolympics.entities.GroupEntity;
import it.plantict.officeolympics.entities.UserEntity;
import it.plantict.officeolympics.entities.UserGroupEntity;
import it.plantict.officeolympics.mapper.GroupMapper;
import it.plantict.officeolympics.mapper.UserGroupMapper;
import it.plantict.officeolympics.models.UserGroupRequest;
import it.plantict.officeolympics.repository.GroupRepository;
import it.plantict.officeolympics.repository.UserGroupRepository;
import it.plantict.officeolympics.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;

    @Override
    public UserGroupDTO createUserToGroup(UserGroupRequest userGroupRequest) {
        if (userGroupRequest.getIdUser() == null) {
            log.error("Cannot create new UserGroup with null IdUser");
            throw new IllegalArgumentException("IdUser must not be null");
        } else if (userGroupRequest.getIdGroup() == null) {
            log.error("Cannot create new UserGroup with null IdGroup");
            throw new IllegalArgumentException("IdGroup must not be null");
        }
        else {
            UserEntity user = userRepository.findById(userGroupRequest.getIdUser()).orElseThrow(() -> new EntityNotFoundException("IdUser not found"));
            GroupEntity group = groupRepository.findById(userGroupRequest.getIdGroup()).orElseThrow(() -> new EntityNotFoundException("IdGroup not found"));
            UserGroupEntity userGroup = new UserGroupEntity();

            userGroup.setUser(user);
            userGroup.setUserGroup(group);

            userGroup = userGroupRepository.save(userGroup);

            log.info("Made a post request for creating the group with ID_user_group: {}", userGroup.getIdUserGroup());

            return UserGroupMapper.INSTANCE.toDTO(userGroup);
        }
    }

    @Override
    public UserGroupDTO updateUserToGroup(UUID idUserGroup, UUID newUserId, UUID newGroupId) {
        UserEntity newUser = userRepository.findById(newUserId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        GroupEntity newGroup = groupRepository.findById(newGroupId).orElseThrow(() -> new EntityNotFoundException("Group not found"));

        UserGroupEntity userGroup = userGroupRepository.findById(idUserGroup).orElseThrow(() -> new EntityNotFoundException("UserGroup not found"));
        userGroup.setUser(newUser);
        userGroup.setUserGroup(newGroup);

        log.info("Made a put request for updating an existing group with ID_user_group: {}", idUserGroup);

        UserGroupEntity newUserGroup = userGroupRepository.save(userGroup);
        return UserGroupMapper.INSTANCE.toDTO(newUserGroup);
    }

    @Override
    public UserGroupDTO getUserGroupById(UUID idUserGroup)
    {
        UserGroupEntity userGroupEntity = userGroupRepository.findById(idUserGroup).orElseThrow(() -> new EntityNotFoundException("Group not found"));

        log.info("Made a getById request to get the group with ID_user_group: {}", idUserGroup);

        return UserGroupMapper.INSTANCE.toDTO(userGroupEntity);
    }

    private PageRequest createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

    @Override
    public List<UserGroupDTO> getAllUserGroups(int page, int size) {
        Pageable pageRequest = createPageRequestUsing(page, size);

        Page<UserGroupEntity> allUserGroups = userGroupRepository.findAll(pageRequest);

        List<UserGroupDTO> contentDTO = UserGroupMapper.INSTANCE.mapToDTOs(allUserGroups.toList());
        return contentDTO;
    }

    @Override
    public long getCountAllUserGroups()
    {
        return userGroupRepository.count();
    }

    @Override
    public void deleteUserGroup(UUID idUserGroup)
    {
        GroupEntity groupDTO = groupRepository.findById(idUserGroup).orElseThrow(() -> new EntityNotFoundException("Group not found"));

        log.info("Made a delete request for removing the existing group with ID_user_group: {}", idUserGroup);
        userGroupRepository.deleteById(idUserGroup);
    }
}

