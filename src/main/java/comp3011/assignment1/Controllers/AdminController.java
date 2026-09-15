package comp3011.assignment1.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final ServerStateService serverStateService;
    public AdminController(ServerStateService serverStateService) {
        this.serverStateService = serverStateService;
    }
    @GetMapping("/uptime")
    public long getUptime() {
        return serverStateService.getUptimeSeconds();
    }
}