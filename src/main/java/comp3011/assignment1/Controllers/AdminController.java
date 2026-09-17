package comp3011.assignment1.Controllers;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comp3011.assignment1.Service.ServerStateService;
import comp3011.assignment1.response.UptimeResponse;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final ServerStateService serverStateService;
    public AdminController(ServerStateService serverStateService) {
        this.serverStateService = serverStateService;
    }
    @GetMapping("/uptime")
    public UptimeResponse getUptime() {
        Instant utcNow = Instant.now();
        
        return new UptimeResponse(serverStateService.getStartTime(),
        		utcNow,
        		serverStateService.getUptimeSeconds());
    }
}