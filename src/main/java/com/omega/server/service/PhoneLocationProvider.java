package com.omega.server.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("phoneLocationProvider")
public class PhoneLocationProvider implements LocationProvider {
    @Override
    public double[] getLocation() {
        // Implementación para obtener la ubicación desde un teléfono móvil
        return new double[]{19.4326, -99.1332};  // Ejemplo de latitud y longitud
    }
}
