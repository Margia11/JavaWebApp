package it.plantict.officeolympics.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public abstract class BaseDTO {

    protected Timestamp insertTimestamp;
    protected Timestamp lastUpdate;
    protected String operator;
    private String lastSpanId;

}
