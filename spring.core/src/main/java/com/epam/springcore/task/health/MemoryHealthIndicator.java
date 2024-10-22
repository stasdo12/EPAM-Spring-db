package com.epam.springcore.task.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MemoryHealthIndicator implements HealthIndicator {
    private static final long MEMORY_THRESHOLD = 20 * 1024 * 1024;

    @Override
    public Health health() {
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long usedMemory = totalMemory - freeMemory;

        if (freeMemory >= MEMORY_THRESHOLD) {
            return Health.up()
                    .withDetail("memoryStatus", "Sufficient memory available")
                    .withDetail("freeMemory", freeMemory / (1024 * 1024) + " MB")
                    .withDetail("totalMemory", totalMemory / (1024 * 1024) + " MB")
                    .withDetail("maxMemory", maxMemory / (1024 * 1024) + " MB")
                    .withDetail("usedMemory", usedMemory / (1024 * 1024) + " MB")
                    .build();
        } else {
            return Health.down()
                    .withDetail("memoryStatus", "Low memory")
                    .withDetail("freeMemory", freeMemory / (1024 * 1024) + " MB")
                    .withDetail("totalMemory", totalMemory / (1024 * 1024) + " MB")
                    .withDetail("maxMemory", maxMemory / (1024 * 1024) + " MB")
                    .withDetail("usedMemory", usedMemory / (1024 * 1024) + " MB")
                    .build();
        }
    }

}