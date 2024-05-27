package it.plantict.officeolympics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoleDTO extends BaseDTO {

    private UUID idRole;
    private String name;
    private String description;

}
