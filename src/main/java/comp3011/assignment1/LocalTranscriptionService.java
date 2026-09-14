package comp3011.assignment1;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Profile("local")
public class LocalTranscriptionService implements TranscriptionService {

    @Override
    public String transcribeAudio(MultipartFile audio) {

        return """
                {
                    "text": "Local stub transcription"
                }
                """;
    }
}