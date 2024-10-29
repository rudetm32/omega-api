package com.omega.server.controller;


import com.omega.server.domain.location.Location;
import com.omega.server.domain.location.VehicleWithLocationDTO;
import com.omega.server.service.LocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleLocationController {

    private final LocationService locationService;

    public VehicleLocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/{vehicleId}/location")
    public VehicleWithLocationDTO getVehicleWithLocations(@PathVariable Long vehicleId) {
        return locationService.getAllVehiclesWithLocations(vehicleId);
    }
}
