package it.plantict.officeolympics.controllers;

import it.plantict.officeolympics.dto.ChallengeDTO;
import it.plantict.officeolympics.exceptions.ExceptionHandler;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.models.ChallengeRequest;
import it.plantict.officeolympics.models.SearchContainer;
import it.plantict.officeolympics.models.Sort;
import it.plantict.officeolympics.service.ChallengeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {

    @Autowired(required = true)
    private ChallengeService service;

    private final Logger logger = LogManager.getLogger(ExceptionHandler.class);

    @GetMapping("/list")
    public ResponseEntity<SearchContainer<ChallengeDTO>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        logger.info("GET call for challenge table");
        long total = service.totalChallenge();
        List<ChallengeDTO> challengeDTOS = service.getAllChallenges(page, size);
        Sort sort = new Sort();
        //sort.setField("");
        //sort.setOrder("");
        SearchContainer<ChallengeDTO> list = new SearchContainer<>(total, page, size, sort, challengeDTOS);
        logger.info("GET successful for all challenges: {}", challengeDTOS);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ChallengeDTO> getByName(@PathVariable String name) {
        logger.info("GET for challenge with name: {}", name);
        ChallengeDTO challenge = service.getChallengeByName(name);
        return ResponseEntity.ok(challenge);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ChallengeDTO>> getByUser(@PathVariable UUID id) {
        logger.info("GET for all challenges assigned to user: {}", id);
        List<ChallengeDTO> challengeDTOS = service.getAllByUser(id);
        return ResponseEntity.ok(challengeDTOS);
    }

    @PutMapping("/edit")
    public ResponseEntity<ChallengeDTO> putChallenge(UUID idChallenge, @RequestBody ChallengeRequest request) {
        logger.info("PUT call for challenge table");
        ChallengeDTO challengeDTO = service.updateChallenge(idChallenge, request);
        logger.info("PUT successful for challenge with name: {}", challengeDTO.getName());
        return new ResponseEntity<>(challengeDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ChallengeDTO> postChallenge(@RequestBody ChallengeRequest request) throws MyDuplicateKeyException {
        logger.info("POST call for challenge table");
        ChallengeDTO challengeDTO = service.createChallenge(request);
        logger.info("POST successful for a new challenge with name: {}", challengeDTO.getName());
        return new ResponseEntity<>(challengeDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteChallenge(UUID id) {
        logger.info("DELETE call for challenge table");
        service.destroyChallenge(id);
        logger.info("challenge with id {} deleted successfully", id);
    }
}
