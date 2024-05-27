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
@Table(name = "companies", schema = "officeolympicsdb")
@Getter
@Setter
public class CompanyEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_company", nullable = false)
    private UUID idCompany;

    @NotNull
    @Column(name = "name")
    @Size(max = 100)
    private String name;

    @Column(name = "vat_number")
    @Size(max = 1000)
    private String vatNumber;

    @Column(name = "description")
    @Size(max = 1000)
    private String description;

    @Column(name = "logo")
    private String logo;

    @OneToMany(mappedBy = "company")
    private List<UserCompanyEntity> userCompany;

    @OneToMany(mappedBy = "company")
    private List<GroupEntity> groups;
}
