package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.UserGroupDTO;
import it.plantict.officeolympics.models.UserGroupRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;


public interface UserGroupService {
    UserGroupDTO createUserToGroup(UserGroupRequest userGroupRequest);

    UserGroupDTO updateUserToGroup(UUID idUserGroup, UUID newUserId, UUID newGroupId);

    UserGroupDTO getUserGroupById(UUID idUserGroup);

    List<UserGroupDTO> getAllUserGroups(int page, int size);

    long getCountAllUserGroups();

    void deleteUserGroup(UUID idUserGroup);

}
