package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.GroupDTO;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.models.GroupRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface GroupService {

    GroupDTO createGroup(GroupRequest groupRequest) throws MyDuplicateKeyException;

    GroupDTO updateGroup(UUID idGroup, GroupDTO groupDTO);

    GroupDTO getGroupById(UUID idGroup);

    List<GroupDTO> getAllGroups(int page, int size);

    long getCountAllGroups();

    void deleteGroup(UUID idGroup);
}
