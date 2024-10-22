package com.epam.springcore.task.health.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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