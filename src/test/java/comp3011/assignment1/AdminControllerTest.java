package comp3011.assignment1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import comp3011.assignment1.Controllers.AdminController;
import comp3011.assignment1.Service.ServerStateService;
import comp3011.assignment1.response.UptimeResponse;

class AdminControllerTest {

    @Test
    void getUptimeReturnsServerUptimeInformation() {

        ServerStateService serverStateService = new ServerStateService();
        AdminController controller = new AdminController(serverStateService);
        UptimeResponse response = controller.getUptime();

        assertNotNull(response);
        assertNotNull(response.getUtcServerStart());
        assertNotNull(response.getUtcNow());
        assertEquals(
                serverStateService.getStartTime(),
                response.getUtcServerStart()
        );

        assertTrue(
                response.getUtcNow().isAfter(response.getUtcServerStart())
                        || response.getUtcNow().equals(response.getUtcServerStart())
        );

        assertTrue(response.getServerUptimeSeconds() >= 0);
    }
}