package com.omega.server.controller;


import com.omega.server.domain.location.Location;
import com.omega.server.domain.location.LocationDTO;
import com.omega.server.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleLocationController {

    private final LocationService locationService;

    public VehicleLocationController(LocationService locationService) {
        this.locationService = locationService;
    }
    @GetMapping("/{vehicleId}/locations")
    public ResponseEntity<Page<LocationDTO>> getVehicleLocations(@PathVariable Long vehicleId, @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @PageableDefault(size = 10, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Location> locations = locationService.getVehicleLocations(vehicleId, from, to, pageable);
        Page<LocationDTO> locationDTOs = locations.map(LocationDTO::new);
        return ResponseEntity.ok(locationDTOs);
    }
}
