package com.epam.springcore.task.health.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class RequestMetrics {
    private final Counter requestCounter;

    public RequestMetrics(MeterRegistry meterRegistry) {
        this.requestCounter = meterRegistry.counter("custom_requests_total");
    }

    public void incrementRequestCount() {
        requestCounter.increment();
    }

}
