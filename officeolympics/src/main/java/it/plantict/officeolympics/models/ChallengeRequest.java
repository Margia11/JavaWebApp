package it.plantict.officeolympics.models;

import lombok.Getter;

import java.util.UUID;


@Getter
public class ChallengeRequest {

    private UUID    idUser;
    private String  name;
    private String  description;
}
