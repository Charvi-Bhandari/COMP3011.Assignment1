package comp3011.assignment1.Controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import comp3011.assignment1.Service.TranscriptionService;

@RestController
public class AudioController {

    private final TranscriptionService transcriptionService;

    public AudioController(TranscriptionService transcriptionService) {
        this.transcriptionService = transcriptionService;
    }

    @PostMapping("/api/audio")
    public CompletableFuture<String> recieveAudio(
            @RequestParam("audio") MultipartFile audio) {

        return transcriptionService.transcribeAudio(audio);
    }
}