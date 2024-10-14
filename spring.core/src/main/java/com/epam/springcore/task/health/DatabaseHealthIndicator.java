package com.epam.springcore.task.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseHealthIndicator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Health health() {
        boolean isDatabaseUp = checkDatabaseConnection();

        if (isDatabaseUp) {
            return Health.up().withDetail("database", "Database is up").build();
        } else {
            return Health.down().withDetail("database", "Database is down").build();
        }
    }

    private boolean checkDatabaseConnection() {
        try {

            jdbcTemplate.execute("SELECT 1");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}