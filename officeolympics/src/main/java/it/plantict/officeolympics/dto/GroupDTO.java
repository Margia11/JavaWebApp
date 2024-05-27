package it.plantict.officeolympics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GroupDTO extends BaseDTO {

    private UUID        idGroup;
    private CompanyDTO  company;
    private String      type;
    private String      name;
    private String      description;

}
