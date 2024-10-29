package com.omega.server.domain.location;

import java.time.LocalDateTime;

public record LocationDTO(double latitude, double longitude, LocalDateTime timestamp) {
    // Este constructor es implícito en los records.
    public LocationDTO(Location location) {
        this(location.getLatitude(), location.getLongitude(), location.getTimestamp());
    }
}

