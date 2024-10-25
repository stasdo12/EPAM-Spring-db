package com.epam.springcore.task.health;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.Health;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

class DatabaseHealthIndicatorTest {

    private DatabaseHealthIndicator databaseHealthIndicator;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {

        jdbcTemplate = Mockito.mock(JdbcTemplate.class);
        databaseHealthIndicator = new DatabaseHealthIndicator(jdbcTemplate);
    }

    @Test
    void shouldReturnHealthUpWhenDatabaseIsUp() {
        doNothing().when(jdbcTemplate).execute("SELECT 1");

        Health health = databaseHealthIndicator.health();

        assertEquals("UP", health.getStatus().getCode());
        assertEquals("Database is up", health.getDetails().get("database"));
    }

    @Test
    void shouldReturnHealthDownWhenDatabaseIsDown() {
        doThrow(new RuntimeException("Database connection failed")).when(jdbcTemplate).execute("SELECT 1");

        Health health = databaseHealthIndicator.health();

        assertEquals("DOWN", health.getStatus().getCode());
        assertEquals("Database is down", health.getDetails().get("database"));
    }
}