package comp3011.assignment1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import comp3011.assignment1.Controllers.AudioController;
import comp3011.assignment1.Service.TranscriptionService;

class AudioControllerTest {

    @Test
    void audioControllerReturnsTranscription() {

        TranscriptionService service =
                audio -> CompletableFuture.completedFuture(
                        "test transcription"
                );

        AudioController controller =
                new AudioController(service);

        CompletableFuture<String> result =
                controller.recieveAudio(null);

        assertEquals(
                "test transcription",
                result.join()
        );
    }
}