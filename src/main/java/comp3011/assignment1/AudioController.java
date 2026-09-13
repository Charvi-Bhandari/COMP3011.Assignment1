package comp3011.assignment1;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class audioController {
	@PostMapping("/api/audio")
	public String recieveAudio(@RequestParam("audio") MultipartFile audio) {
		return "audio: " + audio.getSize() + "bytes";
	}
}
