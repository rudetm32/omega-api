package com.omega.server.controller;

import com.omega.server.domain.vehicle.*;
import com.omega.server.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<ResponseVehicleDTO> createVehicle(
            @RequestBody @Valid RegisterVehicleDTO registerVehicleDTO,
            UriComponentsBuilder uriComponentsBuilder) {

        ResponseVehicleDTO response = vehicleService.createVehicleAndMap(registerVehicleDTO);
        URI location = uriComponentsBuilder.path("/vehicles/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ListVehiclesDTO>> listVehicles(@PageableDefault(size = 10, sort = "licensePlate") Pageable pageable) {
        Page<Vehicle> vehicles = vehicleService.listVehiclesSort(pageable);
        Page<ListVehiclesDTO> listVehicles = vehicles.map(ListVehiclesDTO::new);
        return ResponseEntity.ok(listVehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseVehicleDTO> getVehicleById(@PathVariable Long id) {
        ResponseVehicleDTO response = vehicleService.findVehicle(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletedVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long vehicleId, @RequestBody @Valid UpdateVehicleDTO updateVehicleDTO) {
        var vehicleUpdate =vehicleService.updateVehicle(vehicleId, updateVehicleDTO);
        return ResponseEntity.ok(vehicleUpdate);
    }
}
