package com.omega.server.repository;


import com.omega.server.domain.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findTopByVehicleIdOrderByTimestampDesc(Long vehicleId);

    List<Location> findByVehicleId(Long vehicleId);
}
