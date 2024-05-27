package it.plantict.officeolympics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CompanyDTO extends BaseDTO {

    private UUID idCompany;
    private String name;
    private String vatNumber;
    private String description;
    private String logo;

}
