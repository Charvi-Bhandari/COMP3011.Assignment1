package comp3011.assignment1.Controllers;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import comp3011.assignment1.Service.ServerStateService;
import comp3011.assignment1.response.UptimeResponse;
import comp3011.assignment1.Service.ShutdownStateService;
import comp3011.assignment1.response.ErrorResponse;
import comp3011.assignment1.response.ShutDownResponse;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
	

    private final ServerStateService serverStateService;
    private final ShutdownStateService shutdownStateService;
    
    public AdminController(ServerStateService serverStateService, 
    		ShutdownStateService shutdownStateService) {
        this.serverStateService = serverStateService;
        this.shutdownStateService = shutdownStateService;
    }
    @GetMapping("/uptime")
    public UptimeResponse getUptime() {
        Instant utcNow = Instant.now();
        
        return new UptimeResponse(serverStateService.getStartTime(),
        		utcNow,
        		serverStateService.getUptimeSeconds());
    }
    @PostMapping("/shutdown")
    public ResponseEntity<?> shutdown() {

        if (shutdownStateService.requestShutdown()) {

            return ResponseEntity
                    .accepted()
                    .body(new ShutDownResponse("Graceful shutdown requested."));
        }

        return ResponseEntity
                .status(409)
                .body(new ErrorResponse(
                        Instant.now(),
                        409,
                        "Conflict",
                        "Shutdown is already in progress.",
                        "/api/v1/admin/shutdown"
                ));
    }
}