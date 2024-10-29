package com.omega.server.repository;

import com.omega.server.domain.vehicle.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

   
    // Consulta para obtener los vehículos que no están marcados como eliminados
    @Query("SELECT v FROM Vehicle v WHERE v.isDeleted = false")
    Page<Vehicle> findAllActiveVehicles(Pageable pageable);
}
