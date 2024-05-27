package it.plantict.officeolympics.models;

import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
public class ChallengeGroupRequest {

    private UUID      idChallenge;
    private UUID      idGroup;
    private Timestamp startDate;
    private Timestamp endDate;
}
