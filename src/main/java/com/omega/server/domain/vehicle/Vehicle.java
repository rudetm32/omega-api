package com.omega.server.domain.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omega.server.domain.location.Location;
import com.omega.server.domain.company.Company;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vehicles")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", unique = true, nullable = false)
    private String licensePlate;

    @Column(name = "economic_number")
    private String economicNumber;

    private String model;  // Modelo del vehículo

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    @Setter
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;


    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> locations;

    // Constructor con los valores asignados correctamente


    public Vehicle(Long id, String licensePlate, String model, String economicNumber, Boolean isDeleted) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.model = model;
        this.economicNumber = economicNumber;
        this.isDeleted = isDeleted;
    }



    // Método para actualizar atributos del vehículo
    public void updateVehicleAttributes(UpdateVehicleDTO vehicleUpdateData) {
        if (vehicleUpdateData.isDeleted() != null) {
            this.isDeleted = vehicleUpdateData.isDeleted();
        }
        if (vehicleUpdateData.licensePlate() != null) {
            this.licensePlate = vehicleUpdateData.licensePlate();
        }
        if (vehicleUpdateData.economicNumber() != null) {
            this.economicNumber = vehicleUpdateData.economicNumber();
        }
        if (vehicleUpdateData.model() != null) {
            this.model = vehicleUpdateData.model();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getEconomicNumber() {
        return economicNumber;
    }

    public void setEconomicNumber(String economicNumber) {
        this.economicNumber = economicNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
