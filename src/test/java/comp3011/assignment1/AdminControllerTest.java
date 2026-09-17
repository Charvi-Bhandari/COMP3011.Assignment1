package comp3011.assignment1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.springframework.http.ResponseEntity;
import org.springframework.context.ConfigurableApplicationContext;

import comp3011.assignment1.Controllers.AdminController;
import comp3011.assignment1.Service.ServerStateService;
import comp3011.assignment1.Service.ShutdownStateService;
import comp3011.assignment1.response.ErrorResponse;
import comp3011.assignment1.response.ShutDownResponse;
import comp3011.assignment1.response.UptimeResponse;

class AdminControllerTest {

    @Test
    void getUptimeReturnsServerUptimeInformation() {

        ServerStateService serverStateService = new ServerStateService();
        ShutdownStateService shutdownStateService = new ShutdownStateService();
        
        ConfigurableApplicationContext applicationContext =
                mock(ConfigurableApplicationContext.class);
        
        AdminController controller = new AdminController(serverStateService, shutdownStateService, applicationContext);
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
    
    @Test
    void shutdowAcceptedFirstRequest() {
        ServerStateService serverStateService = new ServerStateService();
        ShutdownStateService shutdownStateService = new ShutdownStateService();
        
        ConfigurableApplicationContext applicationContext =
                mock(ConfigurableApplicationContext.class);

        AdminController controller = new AdminController(
                serverStateService,
                shutdownStateService,
                applicationContext
        );
        ResponseEntity<?> response = controller.shutdown();

        assertEquals(202, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof ShutDownResponse);

        ShutDownResponse shutdownResponse =
                (ShutDownResponse) response.getBody();
        assertEquals(
                "Graceful shutdown requested.",
                shutdownResponse.getMessage()
        );
    }
    @Test
    void shutdownReturnCnflctAlreadyRequested() {
        ServerStateService serverStateService = new ServerStateService();
        ShutdownStateService shutdownStateService = new ShutdownStateService();
        
        ConfigurableApplicationContext applicationContext =
                mock(ConfigurableApplicationContext.class);
        
        AdminController controller = new AdminController(
                serverStateService,
                shutdownStateService,
                applicationContext
        );

        controller.shutdown();
        ResponseEntity<?> response = controller.shutdown();

        assertEquals(409, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof ErrorResponse);

        ErrorResponse errorResponse =
                (ErrorResponse) response.getBody();

        assertEquals(409, errorResponse.getStatus());
        assertEquals("Conflict", errorResponse.getError());
        assertEquals(
                "Shutdown is already in progress.",
                errorResponse.getMessage()
        );
        assertEquals(
                "/api/v1/admin/shutdown",
                errorResponse.getPath()
        );
    }
    
    @Test
    void shutdownCloseAppContext() throws InterruptedException {

        ServerStateService serverStateService = new ServerStateService();
        ShutdownStateService shutdownStateService = new ShutdownStateService();

        ConfigurableApplicationContext applicationContext =
                mock(ConfigurableApplicationContext.class);

        AdminController controller = new AdminController(
                serverStateService,
                shutdownStateService,
                applicationContext
        );

        controller.shutdown();

        Thread.sleep(500);

        verify(applicationContext).close();
    }
}