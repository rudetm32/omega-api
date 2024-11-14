package com.omega.server.domain.location;

import com.omega.server.domain.vehicle.Vehicle;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Location")
@Table(name="locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Establece la relación con Vehicle
    @JoinColumn(name = "vehicle_id", nullable = false) // Relación con Vehicle
    private Vehicle vehicle;

    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;

    public Location() {}

    public Location(Vehicle vehicle, double latitude, double longitude) {
        this.vehicle = vehicle;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = LocalDateTime.now();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
