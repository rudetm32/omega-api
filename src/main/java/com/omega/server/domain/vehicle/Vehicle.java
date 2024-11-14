package com.omega.server.domain.vehicle;


import com.omega.server.domain.location.Location;
import com.omega.server.domain.company.Company;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private String ecoNum;

    private String model;  // Modelo del vehículo

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    @Setter
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> locations;


//    public Vehicle() {
//
//    }

    // Constructor
    public Vehicle(Long id, String licensePlate, String model, String ecoNum, Boolean isDeleted) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.model = model;
        this.ecoNum = ecoNum;
        this.isDeleted = isDeleted;
    }

    //Getter and Setter
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

    public String getEcoNum() {
        return ecoNum;
    }

    public void setEcoNum(String ecoNum) {
        this.ecoNum = ecoNum;
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




    // Método para actualizar atributos del vehículo
    public void updateVehicleAttributes(UpdateVehicleDTO vehicleUpdateData) {
        if (vehicleUpdateData.isDeleted() != null) {
            this.isDeleted = vehicleUpdateData.isDeleted();
        }
        if (vehicleUpdateData.licensePlate() != null) {
            this.licensePlate = vehicleUpdateData.licensePlate();
        }
        if (vehicleUpdateData.ecoNum() != null) {
            this.ecoNum = vehicleUpdateData.ecoNum();
        }
        if (vehicleUpdateData.model() != null) {
            this.model = vehicleUpdateData.model();
        }
    }
}