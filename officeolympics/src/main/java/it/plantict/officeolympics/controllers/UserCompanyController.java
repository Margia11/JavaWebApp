package it.plantict.officeolympics.controllers;

import it.plantict.officeolympics.dto.CompanyDTO;
import it.plantict.officeolympics.dto.UserCompanyDTO;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.models.SearchContainer;
import it.plantict.officeolympics.models.Sort;
import it.plantict.officeolympics.models.UserCompanyRequest;
import it.plantict.officeolympics.service.UserCompanyService;
import jakarta.persistence.EntityNotFoundException;
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

@RequestMapping("/usercompany")
@RestController
public class UserCompanyController {

    @Autowired
    private UserCompanyService userCompanyService;

    private final Logger log = LogManager.getLogger(ExceptionHandler.class);

    @GetMapping("/allusercompanies")
    public ResponseEntity<SearchContainer<UserCompanyDTO>> getAllUserCompanies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size)
    {
        List<UserCompanyDTO> userCompanyPage = userCompanyService.getAllUserCompanies(page, size);
        long count = userCompanyService.getCountAllUserCompanies();
        HttpHeaders headers = new HttpHeaders();

        Sort sort = new Sort();
        sort.setField("type");
        sort.setOrder("desc");
        SearchContainer<UserCompanyDTO> list = new SearchContainer<>(count,page,size,sort,userCompanyPage);

        return ResponseEntity.ok()
                .headers(headers)
                .body(list);
    }

    @GetMapping("/{idUserCompany}")
    public ResponseEntity<UserCompanyDTO> getUserCompanyById(@PathVariable(name = "idUserCompany") UUID IdCompany)
    {
            log.info("Searching company");
            UserCompanyDTO userCompanyDTO = userCompanyService.getUserCompanyByIdCompany(IdCompany);
            return new ResponseEntity<>(userCompanyDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserCompanyDTO> createCompany(@RequestBody UserCompanyRequest userCompanyRequest)
    {
            UserCompanyDTO createdCompany = userCompanyService.createUserCompany(userCompanyRequest);
            return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @PutMapping("/{idUserCompany}")
    public ResponseEntity<UserCompanyDTO> updateCompany(@PathVariable(name = "idUserCompany") UUID idUserCompany, @RequestBody UserCompanyRequest companyRequest) throws MyDuplicateKeyException
    {
            UserCompanyDTO updatedCompany = userCompanyService.updateUserCompany(idUserCompany, companyRequest);
            return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{idUserCompany}")
    public ResponseEntity<Void> deleteCompany(@PathVariable(name = "idUserCompany") UUID idUserCompany)
    {
            userCompanyService.deleteUserCompany(idUserCompany);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
