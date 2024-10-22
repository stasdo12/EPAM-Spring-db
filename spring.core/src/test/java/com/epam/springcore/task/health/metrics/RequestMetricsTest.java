package com.epam.springcore.task.health.metrics;

import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class RequestMetricsTest {

    private RequestMetrics requestMetrics;
    private Counter counter;

    @BeforeEach
    void setUp() {
        MeterRegistry meterRegistry = Mockito.mock(MeterRegistry.class);
        counter = Mockito.mock(Counter.class);

        when(meterRegistry.counter("custom_requests_total")).thenReturn(counter);

        requestMetrics = new RequestMetrics(meterRegistry);
    }

    @Test
    void shouldIncrementRequestCount() {

        requestMetrics.incrementRequestCount();

        verify(counter).increment();
    }
}