package it.plantict.officeolympics.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "challenge", schema = "officeolympicsdb")
@Getter
@Setter
public class ChallengeEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_challenge", nullable = false )
    private UUID idChallenge;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonBackReference
    private UserEntity user;

    @NotNull
    @Column(name = "name")
    @Size(max = 100)
    private String name;

    @Column(name = "description")
    @Size(max = 1000)
    private String description;

    @OneToMany(mappedBy = "challenge")
    private List<ChallengeGroupEntity> challengeGroups;
}
