package it.plantict.officeolympics.models;

import lombok.Getter;

import java.util.UUID;

@Getter
public class GroupRequest {
    private UUID    idCompany;
    private String  type;
    private String  name;
    private String  description;
}
