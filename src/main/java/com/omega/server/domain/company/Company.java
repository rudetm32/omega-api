package com.omega.server.domain.company;

import com.omega.server.domain.address.Address;
import com.omega.server.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity(name = "Company")
@Table(name="companies")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String telephone;

    @Column(name = "name_contact")
    private String nameContact;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<User> users;

    public Company(RegisterCompany data) {
        this.name = data.name();
        this.email = data.email();
        this.telephone = data.telephone();
        this.nameContact = data.nameContact();
        this.isDeleted =data.isDeleted();
        this.address = new Address(data.address());
    }
}
