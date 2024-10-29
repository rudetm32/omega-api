package com.omega.server.service;

import com.omega.server.domain.company.Company;
import com.omega.server.domain.company.ResponseCompaniesNameDTO;
import com.omega.server.domain.vehicle.RegisterVehicleDTO;
import com.omega.server.domain.vehicle.ResponseVehicleDTO;
import com.omega.server.domain.vehicle.UpdateVehicleDTO;
import com.omega.server.domain.vehicle.Vehicle;
import com.omega.server.exception.CustomException;
import com.omega.server.repository.CompanyRepository;
import com.omega.server.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CompanyRepository companyRepository;

    public VehicleService(VehicleRepository vehicleRepository, CompanyRepository companyRepository) {
        this.vehicleRepository = vehicleRepository;
        this.companyRepository = companyRepository;
    }

    public ResponseVehicleDTO createVehicleAndMap(RegisterVehicleDTO registerVehicleDTO) {
        // Buscar la compañía por ID y lanzar excepción si no existe
        Company company = companyRepository.findById(registerVehicleDTO.companyId())
                .orElseThrow(() -> new CustomException("Company not found with ID: " + registerVehicleDTO.companyId()));

        // Crear un nuevo objeto Vehicle y asignar los datos del DTO
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(registerVehicleDTO.licensePlate());
        vehicle.setEconomicNumber(registerVehicleDTO.economicNumber());
        vehicle.setModel(registerVehicleDTO.model());
        vehicle.setCompany(company); // Asigna la compañía encontrada al vehículo

        // Guardar el vehículo en la base de datos
        vehicle = vehicleRepository.save(vehicle);

        // Mapear el vehículo guardado a ResponseVehicleDTO y retornar
        return new ResponseVehicleDTO(
                vehicle.getId(),
                vehicle.getLicensePlate(),
                vehicle.getEconomicNumber(),
                vehicle.getModel(),
                new ResponseCompaniesNameDTO(
                        vehicle.getCompany().getId(),
                        vehicle.getCompany().getName()
                )
        );
    }




    public Page<Vehicle> listVehiclesSort(Pageable pageable) {
        var vehicles = vehicleRepository.findAllActiveVehicles(pageable);
        if(vehicles.isEmpty()){
            throw new CustomException("No active vehicles found");
        }
        return vehicles;
    }

    public ResponseVehicleDTO findVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new CustomException("Vehicle not found with ID: " + id));

        if(Boolean.TRUE.equals(vehicle.getDeleted())){
            throw new CustomException("Vehicle with ID: "+ id + " is deactivated");
        } else {
            return new ResponseVehicleDTO(
                    vehicle.getId(),
                    vehicle.getLicensePlate(),
                    vehicle.getEconomicNumber(),
                    vehicle.getModel(),
                    new ResponseCompaniesNameDTO(vehicle.getCompany().getId(), vehicle.getCompany().getName())
            );
        }
    }


    public void deleteVehicle(Long id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        optionalVehicle.ifPresentOrElse(vehicle -> {
            vehicle.setDeleted(true);
            vehicleRepository.save(vehicle);
        }, ()-> {
            throw new CustomException("Vehicle not found with ID: " + id);
        });
    }

    public Vehicle updateVehicle(Long vehicleId, UpdateVehicleDTO vehicleUpdateData) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleId);

        return vehicleOptional.map(vehicle -> {
            vehicle.updateVehicleAttributes(vehicleUpdateData);
            return vehicleRepository.save(vehicle);
        }).orElseThrow(() -> new CustomException("Vehicle not found with ID: " + vehicleId));
    }
}