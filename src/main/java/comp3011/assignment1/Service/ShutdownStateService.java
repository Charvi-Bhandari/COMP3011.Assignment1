package comp3011.assignment1.Service;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ShutdownStateService {
	
	
	private final AtomicBoolean shutdownInProgress = new AtomicBoolean(false);

    public boolean isShutdownInProgress() {
        return shutdownInProgress.get();
    }
    public boolean requestShutdown() {
        return shutdownInProgress.compareAndSet(false, true);
    }
}
