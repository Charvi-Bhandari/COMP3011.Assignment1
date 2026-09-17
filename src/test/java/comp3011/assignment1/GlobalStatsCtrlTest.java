package comp3011.assignment1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import comp3011.assignment1.Controllers.GlobalStatsController;
import comp3011.assignment1.Service.GlobalStatsService;
import comp3011.assignment1.response.GlobalStatsResponse;

class GlobalStatsCtrlTest {

    @Test
    void globalStatsReturnTokenUsage() {

        GlobalStatsService globalStatsService =
                new GlobalStatsService();

        globalStatsService.addUsage(100, 50);
        globalStatsService.addUsage(25, 10);

        GlobalStatsController controller =
                new GlobalStatsController(globalStatsService);

        GlobalStatsResponse response =
                controller.getGlobalStats();

        assertEquals(125, response.getInputTokens());
        assertEquals(60, response.getOutputTokens());
    }
}