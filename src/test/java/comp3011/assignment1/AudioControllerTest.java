package comp3011.assignment1;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import comp3011.assignment1.Controllers.AudioController;
import comp3011.assignment1.Service.TranscriptionService;

class AudioControllerTest {

	@Test
    void audioControllerReturnsTranscription() {

        TranscriptionService service = audio -> "test transcription";

        AudioController controller =
                new AudioController(service);

        String result = controller.recieveAudio(null);

        assertEquals(
                "test transcription",
                result
        );
    }
}