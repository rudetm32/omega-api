package com.omega.server.domain.company;

import com.omega.server.domain.address.Address;
import com.omega.server.domain.user.User;
import com.omega.server.domain.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "companies")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String telephone;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Company(CompanyDTO data) {
    this.name = data.name();
    this.email = data.email();
    this.telephone = data.telephone();
    this.contactName = data.contactName();
    this.isDeleted = data.isDeleted();
    this.address = new Address(data.address());
}

    public void updateCompanyAttributes(UpdateCompanyDTO updateCompanyDTO) {
        if (updateCompanyDTO.email() != null) {
            this.email = updateCompanyDTO.email();
        }
        if (updateCompanyDTO.contactName() != null) {
            this.contactName = updateCompanyDTO.contactName();
        }
        if (updateCompanyDTO.telephone() != null) {
            this.telephone = updateCompanyDTO.telephone();
        }
        if (updateCompanyDTO.isDeleted() != null) {
            this.isDeleted = updateCompanyDTO.isDeleted();
        }

        // Actualizar la dirección si está presente en el DTO
        if (updateCompanyDTO.address() != null) {
            // Verifica si la dirección existe

            // Actualiza los campos de la dirección
            if (updateCompanyDTO.address().city() != null) {
                this.address.setCity(updateCompanyDTO.address().city());
            }
            if (updateCompanyDTO.address().street() != null) { // Suponiendo que tienes un campo 'street'
                this.address.setStreet(updateCompanyDTO.address().street());
            }
            if (updateCompanyDTO.address().number() != null) { // Suponiendo que tienes un campo 'number'
                this.address.setNumber(updateCompanyDTO.address().number());
            }
            if (updateCompanyDTO.address().state() != null) {
                this.address.setState(updateCompanyDTO.address().state());
            }
            if (updateCompanyDTO.address().zipCode() != null) {
                this.address.setZipCode(updateCompanyDTO.address().zipCode());
            }
        }
    }

}
