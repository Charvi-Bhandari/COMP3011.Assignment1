package comp3011.assignment1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import comp3011.assignment1.Controllers.AudioController;
import comp3011.assignment1.Service.TranscriptionService;

class AudioConcurrencyTest {

	@Test
    void controller250ConcurrentRequests() throws Exception {

        int requestCount = 250;

        CountDownLatch requestsStarted = new CountDownLatch(requestCount);

        CompletableFuture<String> transcriptionResult = new CompletableFuture<>();

        TranscriptionService service = audio -> {
            requestsStarted.countDown();
            return transcriptionResult;
        };

        AudioController controller = new AudioController(service);
        ExecutorService executor = Executors.newFixedThreadPool(requestCount);
        List<Future<CompletableFuture<String>>> requests = new ArrayList<>();

        for (int i = 0; i < requestCount; i++) {
            requests.add(
                    executor.submit(
                            () -> controller.recieveAudio(null)
                    )
            );
        }

        assertTrue(requestsStarted.await(5, TimeUnit.SECONDS));
        transcriptionResult.complete("test transcription");

        for (Future<CompletableFuture<String>> request : requests) {
            assertEquals(
                    "test transcription",
                    request.get().get(5, TimeUnit.SECONDS)
            );
        }

        executor.shutdown();
    }
}


