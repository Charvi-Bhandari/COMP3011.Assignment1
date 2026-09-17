package comp3011.assignment1.Service;

import java.util.concurrent.CompletableFuture;
import org.springframework.web.multipart.MultipartFile;

public interface TranscriptionService {

    CompletableFuture<String> transcribeAudio(MultipartFile audio);
}