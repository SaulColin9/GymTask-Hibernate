package org.example.configuration.indicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.net.InetAddress;

public class PingHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        try {
            byte[] ipAddress = new byte[]{127, 0, 0, 1};
            InetAddress address = InetAddress.getByAddress(ipAddress);
            if (address.isReachable(5000)) {
                return Health.up().withDetail("message", "Remote server is reachable").build();
            } else {
                return Health.down().withDetail("message", "Remote server is not reachable").build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail("message", "Remote server is not reachable").build();
        }
    }
}
