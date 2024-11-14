package com.omega.server.domain.location;

import java.time.LocalDateTime;

public record LocationDTO(Long id, double latitude, double longitude, LocalDateTime timestamp) {
    // Constructor adicional para mapear directamente desde la entidad `Location`
    public LocationDTO(Location location) {
        this(location.getId(), location.getLatitude(), location.getLongitude(), location.getTimestamp());
    }
}
