package com.omega.server.domain.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity(name= "Address")
@Table(name="addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String number;

    private String city;

    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @JsonIgnore
    @Column(name = "is_deleted")
    private boolean isDeleted = false;


    public Address(RegisterAddress data) {
    this.street = data.street();
    this.number = data.number();
    this.city = data.city();
    this.state = data.state();
    this.zipCode = data.zipCode();
    this.isDeleted = data.isDeleted();
    }
}
