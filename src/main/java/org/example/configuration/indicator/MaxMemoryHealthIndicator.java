package org.example.configuration.indicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;

public class MaxMemoryHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        boolean invalid = Runtime.getRuntime().maxMemory() < (100 * 1024 * 1024);
        if (invalid) {
            return Health.status(Status.DOWN)
                    .withDetail("reason", "Reached maximum memory for application")
                    .build();
        }
        return Health.status(Status.UP)
                .withDetail("reason", "Memory available is enough")
                .build();
    }
}