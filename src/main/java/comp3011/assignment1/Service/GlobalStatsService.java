package comp3011.assignment1.Service;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GlobalStatsService {
    private final AtomicLong inputTokens = new AtomicLong(0);
    private final AtomicLong outputTokens = new AtomicLong(0);

    public void addUsage(long inputTokens, long outputTokens) {
        this.inputTokens.addAndGet(inputTokens);
        this.outputTokens.addAndGet(outputTokens);
    }
    public long getInputTokens() {
        return inputTokens.get();
    }
    public long getOutputTokens() {
        return outputTokens.get();
    }
}