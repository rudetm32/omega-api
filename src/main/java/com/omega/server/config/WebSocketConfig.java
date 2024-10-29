package com.omega.server.config;

import com.omega.server.handler.VehicleWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final VehicleWebSocketHandler vehicleWebSocketHandler;

    public WebSocketConfig(VehicleWebSocketHandler vehicleWebSocketHandler) {
        this.vehicleWebSocketHandler = vehicleWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(vehicleWebSocketHandler, "/ws/vehicle")
                .setAllowedOrigins("*");  // Puedes configurar el CORS aquí
    }
}
