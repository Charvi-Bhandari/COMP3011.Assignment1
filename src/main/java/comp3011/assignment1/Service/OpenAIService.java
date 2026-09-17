package comp3011.assignment1.Service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
@Profile("titan")
public class OpenAIService implements TranscriptionService {
    private final GlobalStatsService globalStatsService;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${openai.api.key}")
    private String apiKey;
    public OpenAIService(
            WebClient.Builder webClientBuilder,
            GlobalStatsService globalStatsService,
            ObjectMapper objectMapper) {

        this.webClient = webClientBuilder
                .baseUrl("https://api.openai.com")
                .build();

        this.globalStatsService = globalStatsService;
        this.objectMapper = objectMapper;
    }

    @Override
    public CompletableFuture<String> transcribeAudio(MultipartFile audio) {
        return CompletableFuture.supplyAsync(() -> {

            try {
                return audio.getBytes();
            } catch (Exception e) {
                throw new RuntimeException( "Could not read audio.", e );
            }

        }).thenCompose(audioBytes -> {

            return webClient.post()
                    .uri("/v1/audio/transcriptions")
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(audioBytes)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(this::processResponse)
                    .toFuture();

        });
    }
    private String processResponse(String result) {

        try {

            JsonNode response = objectMapper.readTree(result);

            JsonNode usage = response.path("usage");

            long inputTokens = usage.path("input_tokens").asLong(0);

            long outputTokens = usage.path("output_tokens").asLong(0);

            globalStatsService.addUsage(inputTokens, outputTokens
            );

            return result;

        } catch (Exception e) {
            throw new RuntimeException( "Could not process transcription response.",  e );
        }
    }
}