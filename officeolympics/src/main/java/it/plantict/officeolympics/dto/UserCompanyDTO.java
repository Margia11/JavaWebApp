package it.plantict.officeolympics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserCompanyDTO extends BaseDTO {

    private UUID idUserCompany;
    private UserDTO user;
    private CompanyDTO company;

}
