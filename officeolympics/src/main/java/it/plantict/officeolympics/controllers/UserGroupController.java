package it.plantict.officeolympics.controllers;

import it.plantict.officeolympics.dto.GroupDTO;
import it.plantict.officeolympics.dto.UserGroupDTO;
import it.plantict.officeolympics.models.GroupRequest;
import it.plantict.officeolympics.models.SearchContainer;
import it.plantict.officeolympics.models.Sort;
import it.plantict.officeolympics.service.UserGroupServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import it.plantict.officeolympics.models.UserGroupRequest;

@Slf4j
@RestController
@RequestMapping("/usergroup")
public class UserGroupController {

    @Autowired
    private UserGroupServiceImpl userGroupServiceImpl;

    public UserGroupController(UserGroupServiceImpl userGroupServiceImpl) {
        this.userGroupServiceImpl = userGroupServiceImpl;
    }

    @PostMapping("/add")
    public ResponseEntity<UserGroupDTO> createUserToGroup(@RequestBody UserGroupRequest userGroupRequest) {
        log.info("Made a POST request in UserGroupController");

        UserGroupDTO newUserGroup = userGroupServiceImpl.createUserToGroup(userGroupRequest);
        return ResponseEntity.ok(newUserGroup);
    }

    @GetMapping("/{idUserGroup}")
    public UserGroupDTO getUserGroupById(@PathVariable UUID idUserGroup) {
        log.info("Made a getById request in UserGroupController");

        return userGroupServiceImpl.getUserGroupById(idUserGroup);
    }

    @GetMapping("/list")
    public ResponseEntity<SearchContainer<UserGroupDTO>> getAllUserGroups(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        log.info("PUT request for list of elements. Page n. {}, {} elements per page", page, size);
        List<UserGroupDTO> userGroupPage = userGroupServiceImpl.getAllUserGroups(page, size);
        long count = userGroupServiceImpl.getCountAllUserGroups();
        HttpHeaders headers = new HttpHeaders();

        Sort sort = new Sort();
        sort.setField("type");
        sort.setOrder("desc");
        SearchContainer<UserGroupDTO> list = new SearchContainer<>(count,page,size,sort,userGroupPage);

        return ResponseEntity.ok()
                .headers(headers)
                .body(list);
    }

    @PutMapping("/edit/{idUserGroup}")
    public UserGroupDTO updateGroup(@PathVariable UUID idUserGroup, @RequestBody UserGroupRequest userGroupRequest) {
        log.info("Made a put request in UserGroupController");

        return userGroupServiceImpl.updateUserToGroup(idUserGroup, userGroupRequest.getIdUser(), userGroupRequest.getIdGroup());
    }

    @DeleteMapping("/delete/{idUserGroup}")
    public void deleteGroup(@PathVariable UUID idUserGroup) {
        log.info("Made a DELETE request in UserGroupController");

        userGroupServiceImpl.deleteUserGroup(idUserGroup);
    }
}
