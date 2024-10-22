package com.epam.springcore.task.health.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExecutionTimeMetricsTest {

    private ExecutionTimeMetrics executionTimeMetrics;
    private Timer timer;

    @BeforeEach
    void setUp() {
        MeterRegistry meterRegistry = mock(MeterRegistry.class);
        timer = mock(Timer.class);

        when(meterRegistry.timer("custom_execution_time")).thenReturn(timer);

        executionTimeMetrics = new ExecutionTimeMetrics(meterRegistry);
    }

    @Test
    void shouldRecordExecutionTime() {

        Runnable runnable = mock(Runnable.class);

        executionTimeMetrics.recordExecutionTime(runnable);
        verify(timer).record(runnable);
    }
}