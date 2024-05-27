package it.plantict.officeolympics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserGroupDTO extends BaseDTO {

    private UUID idUserGroup;
    private UserDTO user;
    private GroupDTO userGroup;

}
