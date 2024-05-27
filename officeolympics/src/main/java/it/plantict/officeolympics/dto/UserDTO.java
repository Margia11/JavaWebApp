package it.plantict.officeolympics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDTO extends BaseDTO {

    private UUID idUser;
    private RoleDTO role;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;

}
