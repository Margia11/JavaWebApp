package it.plantict.officeolympics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "challenge_stats", schema = "officeolympicsdb")
@Getter
@Setter
public class ChallengeStatEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_challenge_stat" , nullable = false )
    private UUID idChallengeStat;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_challenge_group")
    private ChallengeGroupEntity challengeGroup;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user_group")
    private UserGroupEntity usersGroup;

    @NotNull
    @Column(name = "status")
    @Size(max = 100)
    private String status;

    @NotNull
    @Column(name = "points")
    private Integer points;
}
