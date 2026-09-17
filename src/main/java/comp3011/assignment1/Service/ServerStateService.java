package comp3011.assignment1.Service;
import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;

@Service
public class ServerStateService {

    private final Instant startTime;
    public ServerStateService() {
        startTime = Instant.now();
    }

    public Instant getStartTime() {
        return startTime;
    }
    public double getUptimeSeconds() {
        Duration uptime = Duration.between(startTime, Instant.now());
        return uptime.toNanos() / 1_000_000_000.0;
    }
}