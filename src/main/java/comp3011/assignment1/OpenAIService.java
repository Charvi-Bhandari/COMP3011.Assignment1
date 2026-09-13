package comp3011.assignment1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenAIService {
	@Value("${openai.api.key}")
	private String apiKey;
	private final RestClient restClient;
	public OpenAIService() {
		restClient = RestClient.builder().baseUrl("https://api.openai.com").build();
	}

}
