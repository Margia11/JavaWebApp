package it.plantict.officeolympics.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public abstract class BaseEntity{

    @JsonIgnore
    @Column(name="insert_timestamp", updatable = false)
    protected Timestamp insertTimestamp;

    @JsonIgnore
    @Column(name="last_update_timestamp")
    protected Timestamp lastUpdate;

    @Column(name="operator", columnDefinition = "varchar(255) default 'system'")
    protected String operator;

    @Column(name = "last_span_id")
    @Size(max = 25)
    private String lastSpanId;
}
