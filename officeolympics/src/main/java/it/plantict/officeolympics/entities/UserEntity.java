package it.plantict.officeolympics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "officeolympicsdb")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_user", nullable = false )
    private UUID idUser;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;

    @NotNull
    @Column(name = "username")
    @Size(max = 250)
    private String username;


    @NotNull
    @Column(name = "first_name")
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    @Size(max = 255)
    private String lastName;

    @NotNull
    @Column(name = "email")
    @Size(max = 50)
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @OneToMany(mappedBy = "user")
    private List<ChallengeEntity> challenge;
}
