package comp3011.assignment1;

import org.springframework.web.multipart.MultipartFile;

public interface TranscriptionService {

    String transcribeAudio(MultipartFile audio);
}