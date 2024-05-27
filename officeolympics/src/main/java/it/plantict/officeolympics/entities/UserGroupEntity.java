package it.plantict.officeolympics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "users_groups", schema = "officeolympicsdb")
@Getter
@Setter
public class UserGroupEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_user_group", nullable = false )
    private UUID idUserGroup;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_group")
    private GroupEntity userGroup;

}
