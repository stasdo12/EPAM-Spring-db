package com.epam.springcore.task.health.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

@Component
public class ExecutionTimeMetrics {

    private final Timer executionTimer;

    public ExecutionTimeMetrics(MeterRegistry meterRegistry) {
        this.executionTimer = meterRegistry.timer("custom_execution_time");
    }
    public void recordExecutionTime(Runnable runnable) {
        executionTimer.record(runnable);
    }

}
