package it.plantict.officeolympics.controllers;

import it.plantict.officeolympics.dto.CompanyDTO;
import it.plantict.officeolympics.dto.GroupDTO;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.models.CompanyCreateRequest;
import it.plantict.officeolympics.models.CompanyRequest;
import it.plantict.officeolympics.models.SearchContainer;
import it.plantict.officeolympics.models.Sort;
import it.plantict.officeolympics.service.CompanyService;
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

@RestController
@RequestMapping("/company")
public class CompanyController
{
    @Autowired
    private CompanyService companyService;

    private final Logger log = LogManager.getLogger(ExceptionHandler.class);

    @GetMapping("/allcompanies")
    public ResponseEntity<SearchContainer<CompanyDTO>> getAllCompanies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size)
    {
        List<CompanyDTO> companyPage = companyService.getAllCompanies(page, size);
        long count = companyService.getCountAllCompanies();
        HttpHeaders headers = new HttpHeaders();

        Sort sort = new Sort();
        sort.setField("type");
        sort.setOrder("desc");
        SearchContainer<CompanyDTO> list = new SearchContainer<>(count,page,size,sort,companyPage);

        return ResponseEntity.ok()
                .headers(headers)
                .body(list);
    }

    //ResponseEntity rappresenta l'intera risposta http
    @GetMapping("/{vatNumber}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable(name = "vatNumber") String vatNumber)
    {
        log.info("Searching company");
        CompanyDTO companyDTO = companyService.getCompanyByVatNumber(vatNumber);
        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }

    //RequestBody indica che ciò che gli viene passato viene convertito in companyRequest e poi passato alla funzione
    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyCreateRequest companyRequest) throws MyDuplicateKeyException {
        CompanyDTO createdCompany = companyService.createCompany(companyRequest);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    //PathVariable estrae il valore della variabile passata
    @PutMapping("/{vatNumber}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable(name = "vatNumber") String vatNumber, @RequestBody CompanyRequest companyRequest)
    {
        CompanyDTO updatedCompany = companyService.updateCompany(vatNumber, companyRequest);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{vatNumber}")
    public ResponseEntity<Void> deleteCompany(@PathVariable(name = "vatNumber") String vatNumber) {
        companyService.deleteCompany(vatNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
