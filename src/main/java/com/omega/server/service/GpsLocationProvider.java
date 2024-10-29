package com.omega.server.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("gpsLocationProvider")
public class GpsLocationProvider implements LocationProvider {
    @Override
    public double[] getLocation() {
        // Implementación para obtener la ubicación de un GPS
        return new double[]{19.4326, -99.1332};  // Ejemplo de latitud y longitud
    }
}
