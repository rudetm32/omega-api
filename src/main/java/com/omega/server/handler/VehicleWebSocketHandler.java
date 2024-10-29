package com.omega.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.server.domain.location.VehicleLocationDTO;
import com.omega.server.service.LocationService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VehicleWebSocketHandler extends TextWebSocketHandler {

    private final LocationService locationService;
    private final ObjectMapper objectMapper;
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    public VehicleWebSocketHandler(LocationService locationService, ObjectMapper objectMapper) {
        this.locationService = locationService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);  // Añade la sesión cuando se establece la conexión.
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        VehicleLocationDTO location = objectMapper.readValue(message.getPayload(), VehicleLocationDTO.class);

        // Actualiza la ubicación del vehículo en la base de datos
        locationService.updateLocation(location);

        // Distribuye la actualización a todos los clientes conectados
        broadcastLocation(location);
    }

    private void broadcastLocation(VehicleLocationDTO location) {
        sessions.forEach(session -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(location)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);  // Elimina la sesión al cerrar la conexión.
    }
}
