package it.plantict.officeolympics.models;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserRequest {
    private UUID idRole;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private String operator;
}
