package com.omega.server.repository;


import com.omega.server.domain.location.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findTopByVehicleIdOrderByTimestampDesc(Long vehicleId);

    List<Location> findByVehicleId(Long vehicleId);

    Page<Location> findByVehicleIdAndTimestampBetween(Long vehicleId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
