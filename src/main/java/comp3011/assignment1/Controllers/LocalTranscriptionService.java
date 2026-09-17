package comp3011.assignment1.Controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import comp3011.assignment1.Service.TranscriptionService;

@Service
@Profile("local")
public class LocalTranscriptionService implements TranscriptionService {

    @Override
    public CompletableFuture<String> transcribeAudio(MultipartFile audio) {

        return CompletableFuture.completedFuture("""
                {
                    "text": "Local stub transcription"
                }
                """);
    }
}