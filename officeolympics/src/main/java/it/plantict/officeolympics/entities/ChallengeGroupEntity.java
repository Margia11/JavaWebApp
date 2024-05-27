package it.plantict.officeolympics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "challenge_groups", schema = "officeolympicsdb")
@Getter
@Setter
public class ChallengeGroupEntity extends BaseEntity{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_challenge_group" , nullable = false )
    private UUID idChallengeGroup;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_challenge")
    private ChallengeEntity challenge;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_group")
    private GroupEntity challengeGroup;

    @NotNull
    @Column(name = "start_date")
    private Timestamp startDate;

    @NotNull
    @Column(name = "end_date")
    private Timestamp endDate;

    @OneToMany(mappedBy = "challengeGroup")
    private List<ChallengeStatEntity> challengeStats;
}
