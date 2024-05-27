package it.plantict.officeolympics.controllers;

import it.plantict.officeolympics.dto.RoleDTO;
import it.plantict.officeolympics.dto.UserDTO;
import it.plantict.officeolympics.exceptions.ExceptionHandler;
import it.plantict.officeolympics.models.RoleRequest;
import it.plantict.officeolympics.models.SearchContainer;
import it.plantict.officeolympics.models.Sort;
import it.plantict.officeolympics.service.RoleServiceImpl;
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
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    private final Logger logger = LogManager.getLogger(ExceptionHandler.class);

    @RequestMapping(method = RequestMethod.GET, path = "/list")
    public ResponseEntity<SearchContainer<RoleDTO>> getAllRolesPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        logger.info("GET/allPage call for role entity");
        List<RoleDTO> rolePages = roleService.getAllRoles(page, size);
        logger.info("GET/allPage successful");
        long count = roleService.getCountAllRoles();
        HttpHeaders headers = new HttpHeaders();

        Sort sort = new Sort();
        sort.setField("type");
        sort.setOrder("desc");
        SearchContainer<RoleDTO> list = new SearchContainer<>(count, page, size, sort, rolePages);
        return ResponseEntity.ok().headers(headers).body(list);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable("id") UUID id) {
        logger.info("GET call for Role with id {}", id);
        RoleDTO foundRole = roleService.getRoleById(id);
        logger.info("GET successful. result: {}", foundRole);
        return new ResponseEntity<>(foundRole, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,  path = "/add")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleRequest roleRequest) {
        logger.info("POST call for Role");
        RoleDTO createdRole = roleService.createRole(roleRequest);
        logger.info("POST successful. result: {}", createdRole);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "edit/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable("id") UUID id, @RequestBody RoleRequest roleRequest) {
        logger.info("UPDATE call for role with id {}", id);
        RoleDTO updateRole = roleService.updateRoleById(id, roleRequest);
        logger.info("UPDATE successful. result: {}", updateRole);
        return new ResponseEntity<>(updateRole, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<?> removeById(@PathVariable("id") UUID id) {
        logger.info("DELETE call for role with id {}", id);
        roleService.removeById(id);
        logger.info("DELETE successful");
        return ResponseEntity.noContent().build();
    }
}
