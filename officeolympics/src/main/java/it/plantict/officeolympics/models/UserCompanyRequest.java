package it.plantict.officeolympics.models;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserCompanyRequest {

    UUID idUser;
    UUID idCompany;
}
