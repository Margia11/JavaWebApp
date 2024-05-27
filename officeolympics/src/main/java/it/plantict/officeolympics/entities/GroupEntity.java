package it.plantict.officeolympics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "groups", schema = "officeolympicsdb")
@Getter
@Setter
public class GroupEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_group", nullable = false )
    private UUID idGroup;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_company")
    private CompanyEntity company;

    @NotNull
    @Column(name = "type")
    @Size(max = 100)
    private String type;

    @NotNull
    @Column(name = "name")
    @Size(max = 100)
    private String name;

    @Column(name = "description")
    @Size(max = 1000)
    private String description;

    @OneToMany(mappedBy = "challengeGroup")
    private List<ChallengeGroupEntity> challengeGroups;

    @OneToMany(mappedBy = "userGroup")
    private List<UserGroupEntity> userGroups;

}
