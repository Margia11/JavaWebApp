package it.plantict.officeolympics.controllers;

import it.plantict.officeolympics.dto.ChallengeGroupDTO;
import it.plantict.officeolympics.exceptions.ExceptionHandler;
import it.plantict.officeolympics.models.ChallengeGroupRequest;
import it.plantict.officeolympics.models.SearchContainer;
import it.plantict.officeolympics.models.Sort;
import it.plantict.officeolympics.service.ChallengeGroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.ap.internal.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/challengegroup")
public class ChallengeGroupController {

    @Autowired
    private ChallengeGroupService service;

    private final Logger logger = LogManager.getLogger(ExceptionHandler.class);

    @GetMapping("/list")
    public ResponseEntity<SearchContainer<ChallengeGroupDTO>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        logger.info("GET call for challengeGroup table");
        long total = service.totalChallengeGroup();
        List<ChallengeGroupDTO> challengeGroupDTOS = service.getAllChallengeGroups(page, size);
        Sort sort = new Sort();
        SearchContainer<ChallengeGroupDTO> list = new SearchContainer<>(total, page, size, sort, challengeGroupDTOS);
        logger.info("GET successful for all challengeGroups: {}", challengeGroupDTOS);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeGroupDTO> getById(@PathVariable UUID id) {
        if(id == null || Strings.isEmpty(String.valueOf(id))){
            logger.warn("id is null or empty");
            //throw new WrongParamsExeception();
        }
        logger.info("GET call for challengeGroup table");
        ChallengeGroupDTO challengeGroupDTO = service.getChallengeGroupById(id);
        logger.info("GET successful for all challengeGroups with specified idChallenge: {}", challengeGroupDTO);
        return ResponseEntity.ok(challengeGroupDTO);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<ChallengeGroupDTO>> getByGroup(@PathVariable UUID id) {
        logger.info("GET for all challengeGroups with user: {}", id);
        List<ChallengeGroupDTO> challengeDTOS = service.getAllByGroup(id);
        return ResponseEntity.ok(challengeDTOS);
    }

    @GetMapping("/challenge/{id}")
    public ResponseEntity<List<ChallengeGroupDTO>> getByUser(@PathVariable UUID id) {
        logger.info("GET for all challengeGroups with challenge: {}", id);
        List<ChallengeGroupDTO> challengeDTOS = service.getAllByChallenge(id);
        return ResponseEntity.ok(challengeDTOS);
    }

    @PostMapping("/add")
    public ResponseEntity<ChallengeGroupDTO> postChallengeGroup(@RequestBody ChallengeGroupRequest request) {
        logger.info("POST call for challengeGroup table");
        ChallengeGroupDTO challengeGroupDTO = service.createChallengeGroup(request);
        logger.info("POST successful for a new challengeGroup.");
        return new ResponseEntity<>(challengeGroupDTO, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<ChallengeGroupDTO> putChallengeGroup(UUID id, @RequestBody ChallengeGroupRequest request) {
        logger.info("PUT call for challengeGroup table");
        ChallengeGroupDTO challengeGroupDTO = service.updateChallengeGroup(id, request);
        logger.info("PUT successful for challengeGroup with id {}", challengeGroupDTO.getIdChallengeGroup());
        return new ResponseEntity<>(challengeGroupDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(UUID id) {
        logger.info("DELETE call for challengeGroup table");
        service.deleteChallengeGroup(id);
        logger.info("DELETE for challengeGroup with id {} completed successfully", id);
    }
}
