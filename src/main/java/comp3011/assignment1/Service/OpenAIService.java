package comp3011.assignment1.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import tools.jackson.databind.ObjectMapper;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@Profile("titan")
public class OpenAIService implements TranscriptionService {
	private final GlobalStatsService globalStatsService;
	@Value("${openai.api.key}")
	private String apiKey;
	private final RestClient restClient;
	private final ObjectMapper objectMapper;
	
	
	public OpenAIService(RestClient.Builder restClientBuilder, 
			GlobalStatsService globalStatsService,
			ObjectMapper objectMapper) {
	    restClient = restClientBuilder.baseUrl("https://api.openai.com").build();
	    this.globalStatsService = globalStatsService;
	    this.objectMapper = objectMapper;
	}
		
	public String transcribeAudio(MultipartFile audio) {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
		formData.add("model", "gpt-4o-mini-transcribe");
		formData.add("file", audio.getResource());
		
		String result = restClient.post()
				.uri("/v1/audio/transcriptions")
				.header("Authorization", "Bearer " + apiKey)
				.contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA)
				.body(formData)
				.retrieve()
				.body(String.class);
		
		return result;
		
	}

}
