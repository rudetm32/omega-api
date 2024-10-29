package com.omega.server.service;

import com.omega.server.domain.location.Location;
import com.omega.server.domain.location.VehicleLocationDTO;
import com.omega.server.domain.location.VehicleWithLocationDTO;
import com.omega.server.domain.vehicle.Vehicle;
import com.omega.server.exception.CustomException;
import com.omega.server.repository.LocationRepository;
import com.omega.server.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final VehicleRepository vehicleRepository;

    public LocationService(LocationRepository locationRepository, VehicleRepository vehicleRepository) {
        this.locationRepository = locationRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public VehicleWithLocationDTO getAllVehiclesWithLocations(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new CustomException("Vehicle not found"));

        List<Location> locations = locationRepository.findByVehicleId(vehicleId);
        List<Location> locationDTOs = locations.stream()
                .map(location -> new Location())
                .collect(Collectors.toList());

        return new VehicleWithLocationDTO(vehicle.getId(), vehicle.getLicensePlate(), vehicle.getEconomicNumber(), vehicle.getModel(), locationDTOs);
    }

    public void updateLocation(VehicleLocationDTO locationDTO) {
        // Recuperar el vehículo usando el ID
        Vehicle vehicle = vehicleRepository.findById(locationDTO.vehicleId())
                .orElseThrow(() -> new CustomException("Vehicle not found"));

        // Crear instancia de Location y guardar en la base de datos
        Location location = new Location(vehicle, locationDTO.latitude(), locationDTO.longitude());
        locationRepository.save(location);
    }

    public Location getLastLocation(Long vehicleId) {
        return locationRepository.findTopByVehicleIdOrderByTimestampDesc(vehicleId);
    }
}

