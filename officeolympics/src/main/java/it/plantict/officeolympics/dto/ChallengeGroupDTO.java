package it.plantict.officeolympics.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class ChallengeGroupDTO extends BaseDTO {

    private UUID            idChallengeGroup;
    private ChallengeDTO    challenge;
    private GroupDTO        challengeGroup;
    private Timestamp       startDate;
    private Timestamp       endDate;
}
