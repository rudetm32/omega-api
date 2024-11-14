package com.omega.server.service;

import com.omega.server.domain.location.Location;
import com.omega.server.domain.location.VehicleLocationDTO;
import com.omega.server.domain.location.VehicleWithLocationDTO;
import com.omega.server.domain.vehicle.Vehicle;
import com.omega.server.infra.exception.CustomException;
import com.omega.server.repository.LocationRepository;
import com.omega.server.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        return new VehicleWithLocationDTO(vehicle.getId(), vehicle.getLicensePlate(), vehicle.getEcoNum(), vehicle.getModel(), locationDTOs);
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

    public Page<Location> getVehicleLocations(Long vehicleId, String from, String to, Pageable pageable) {

        vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new CustomException("Vehicle not found"));

        LocalDateTime startDate;
        LocalDateTime endDate;

        // Define un formateador para parsear las fechas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // Intenta parsear las fechas desde los parámetros
        startDate = from != null ? LocalDateTime.parse(from, formatter) : LocalDateTime.now().minusHours(24);
        endDate = to != null ? LocalDateTime.parse(to, formatter) : LocalDateTime.now();

        // Filtra y devuelve las ubicaciones basadas en vehicleId y el rango de tiempo
        return locationRepository.findByVehicleIdAndTimestampBetween(vehicleId, startDate, endDate, pageable);
    }
}
