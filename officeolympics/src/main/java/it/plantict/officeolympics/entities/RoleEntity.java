package it.plantict.officeolympics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="roles", schema = "officeolympicsdb")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_role", nullable = false )
    private UUID idRole;

    @NotNull
    @Column(name = "name")
    @Size(max = 50)
    private String name;

    @NotNull
    @Column(name = "description")
    @Size(max = 50)
    private String description;

    @OneToMany(mappedBy = "role")
    private List<UserEntity> users;
}
