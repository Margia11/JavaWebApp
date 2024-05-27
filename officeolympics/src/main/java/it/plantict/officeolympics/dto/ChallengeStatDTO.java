package it.plantict.officeolympics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChallengeStatDTO extends BaseDTO {

    private UUID idChallengeStat;
    private ChallengeGroupDTO challengeGroup;
    private UserGroupDTO usersGroup;
    private String status;
    private Integer points;

}
