package org.bea.backend.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class NutrientOpenAiService {

    private final RestClient restClient;

    public NutrientOpenAiService(RestClient.Builder restClient,
                                 @Value("${openai.api.url.base}") String baseUrl,
                                 @Value("${openai.api.key}") String apiKey) {

        this.restClient = restClient
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public String getNutrients(String product, String variation){

        String userPrompt;

        if (variation == null || variation.trim().isEmpty()) {
            userPrompt = "Produktname: " + product +
                    ". Es wurde keine Variation angegeben. Wähle eine realistische, handelsübliche Standardvariation (z.B. '3,5% Fett' für Milch) und trage sie im JSON unter 'variation' ein. Die Variation darf nicht leer oder null sein.";
        } else {
            userPrompt = "Produktname: " + product + ", Variation: " + variation;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String messageResponse = restClient.post()
                .uri("/v1/chat/completions")
                .body(new OpenAiRequest(
                        "gpt-4o-mini",
                        "json_object",
                        0.3,
                        List.of(
                               new OpenAiMessageRequest("system", OpenAiConfig.ingredientRequest),
                               new OpenAiMessageRequest("user", userPrompt)
                        )))
                .retrieve()
                .body(String.class);

        if (!StringUtils.hasText(messageResponse) || !messageResponse.trim().startsWith("{")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OpenAI hat kein valides JSON zurückgegeben (vermutlich verweigert).");
        }
        try {
            JsonNode choiceNode =  objectMapper.readTree(messageResponse).get("choices");
            JsonNode messageNode = choiceNode.get(0).get("message");
            return messageNode.get("content").asText();
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OpenAI-Antwort konnte nicht geparst werden (vermutlich kein gültiges JSON).");
        }
    }
}
