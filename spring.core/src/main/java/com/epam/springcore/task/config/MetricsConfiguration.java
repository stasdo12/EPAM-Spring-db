package com.epam.springcore.task.config;

import com.epam.springcore.task.health.metrics.RequestMetrics;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

    @Bean
    public RequestMetrics requestMetrics(MeterRegistry meterRegistry) {
        return new RequestMetrics(meterRegistry);
    }
}
