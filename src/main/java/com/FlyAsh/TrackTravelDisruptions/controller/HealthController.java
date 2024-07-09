package com.FlyAsh.TrackTravelDisruptions.controller;

import com.FlyAsh.TrackTravelDisruptions.service.RailDataApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RailDataApiService railDataApiService;

    @GetMapping("/health")
    public Map<String, Object> healthCheck() {
        Map<String, Object> healthStatus = new HashMap<>();
        healthStatus.put("status", "UP");

        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1000)) {
                healthStatus.put("database", "UP");
            } else {
                healthStatus.put("database", "DOWN");
            }
        } catch (SQLException e) {
            healthStatus.put("database", "DOWN");
            healthStatus.put("error", e.getMessage());
        }

        // External API health check
        boolean apiHealth = railDataApiService.checkApiHealth();
        healthStatus.put("externalApi", apiHealth ? "UP" : "DOWN");

        return healthStatus;
    }
}