package it.plantict.officeolympics.models;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserGroupRequest {
    private UUID    idUser;
    private UUID    idGroup;
}
