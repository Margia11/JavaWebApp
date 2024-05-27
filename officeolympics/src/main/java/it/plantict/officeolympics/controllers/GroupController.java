package it.plantict.officeolympics.controllers;

import it.plantict.officeolympics.dto.GroupDTO;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.models.GroupRequest;
import it.plantict.officeolympics.models.SearchContainer;
import it.plantict.officeolympics.models.Sort;
import it.plantict.officeolympics.service.GroupServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupServiceImpl groupServiceImpl;

    @PostMapping("/add")
    public GroupDTO createGroup(@RequestBody GroupRequest groupRequest) throws MyDuplicateKeyException {
        log.info("Made a post request in GroupController");

        return groupServiceImpl.createGroup(groupRequest);
    }

    @PutMapping("/edit/{idGroup}")
    public GroupDTO updateGroup(@PathVariable UUID idGroup, @RequestBody GroupDTO groupDTO) {
        log.info("Made a PUT request in GroupController");

        return groupServiceImpl.updateGroup(idGroup, groupDTO);
    }

    @GetMapping("/{idGroup}")
    public GroupDTO getGroupById(@PathVariable UUID idGroup) {
        log.info("Made a getById request in GroupController");

        return groupServiceImpl.getGroupById(idGroup);
    }

    @GetMapping("/list")
    public ResponseEntity<SearchContainer<GroupDTO>> getAllGroups(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        log.info("PUT request for list of elements. Page n. {}, {} elements per page", page, size);
        List<GroupDTO> groupPage = groupServiceImpl.getAllGroups(page, size);
        long count = groupServiceImpl.getCountAllGroups();
        HttpHeaders headers = new HttpHeaders();

        Sort sort = new Sort();
        sort.setField("type");
        sort.setOrder("desc");
        SearchContainer<GroupDTO> list = new SearchContainer<>(count,page,size,sort,groupPage);

        return ResponseEntity.ok()
                .headers(headers)
                .body(list);
    }

    @DeleteMapping("/delete/{idGroup}")
    public void deleteGroup(@PathVariable UUID idGroup) {
        log.info("Made a DELETE request in GroupController");

        groupServiceImpl.deleteGroup(idGroup);
    }

}
