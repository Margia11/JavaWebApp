package it.plantict.officeolympics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChallengeDTO extends BaseDTO {

    private UUID    idChallenge;
    private UserDTO user;
    private String  name;
    private String  description;
}
