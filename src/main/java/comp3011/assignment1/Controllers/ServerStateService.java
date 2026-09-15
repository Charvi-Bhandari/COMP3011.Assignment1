package comp3011.assignment1.Controllers;
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

    public long getUptimeSeconds() {
        return Instant.now().getEpochSecond() - startTime.getEpochSecond();
    }
}