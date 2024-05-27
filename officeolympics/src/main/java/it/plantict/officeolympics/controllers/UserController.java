package it.plantict.officeolympics.controllers;

import it.plantict.officeolympics.dto.UserDTO;
import it.plantict.officeolympics.exceptions.ExceptionHandler;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.models.SearchContainer;
import it.plantict.officeolympics.models.Sort;
import it.plantict.officeolympics.models.UserRequest;
import it.plantict.officeolympics.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    private final Logger logger = LogManager.getLogger(ExceptionHandler.class);

    @RequestMapping(method = RequestMethod.GET, path = "/list")
    public ResponseEntity<SearchContainer<UserDTO>> getAllUsersPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        logger.info("GET/list call for user entity");
        List<UserDTO> userPages = userService.getAllUsers(page, size);
        logger.info("GET/allPage successful");
        long count = userService.getCountAllUsers();
        HttpHeaders headers = new HttpHeaders();

        Sort sort = new Sort();
        sort.setField("type");
        sort.setOrder("desc");
        SearchContainer<UserDTO> list = new SearchContainer<>(count, page, size, sort, userPages);
        return ResponseEntity.ok().headers(headers).body(list);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{username}")
    public ResponseEntity<UserDTO> geUserByFirstName(@PathVariable("username") String username) {
        logger.info("GET call for User with username {}", username);
        UserDTO foundUser = userService.getUserByUsername(username);
        logger.info("GET successful. result: {}", foundUser);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/add")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRequest userRequest) throws MyDuplicateKeyException {
        logger.info("POST call for User");
        UserDTO createdUser = userService.createUser(userRequest);
        logger.info("POST successful. result: {}", createdUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "edit/{id}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable("id") UUID id, @RequestBody UserRequest updateUserRequest) {
        logger.info("UPDATE call for User with id {}", id);
        UserDTO updatedUser = userService.updateUserById(id, updateUserRequest);
        logger.info("UPDATE successful. result: {}", updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{username}")
    public ResponseEntity<?> removeByFirstName(@PathVariable("username") String userName) {
        logger.info("DELETE call for user with username {}", userName);
        userService.removeUserByUsername(userName);
        logger.info("DELETE successful");
        return ResponseEntity.noContent().build();
    }
}