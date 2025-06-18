package org.bea.backend.openAI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bea.backend.exception.OpenAiNotFoundIngredientException;
import org.bea.backend.openai.NutrientOpenAiService;
import org.bea.backend.openai.OpenAiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@AutoConfigureMockRestServiceServer
@ContextConfiguration(classes = OpenAiConfig.class)
class NutrientOpenAiServiceTest {

    private NutrientOpenAiService nutrientOpenAiService;

    private MockRestServiceServer mockServer;

    @Value("${openai.api.url.base}")
    private String baseUrl;
    @Value("${openai.api.key}")
    private String apiKey;

    @BeforeEach
    void setUp(){
        RestClient.Builder builder = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json");

        // Bind MockServer an Builder
        mockServer = MockRestServiceServer.bindTo(builder).build();

        this.nutrientOpenAiService = new NutrientOpenAiService(builder, baseUrl, apiKey);
    }

    @Test
    void getNutrients_returnsCorrectContent() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // given
        String response = String.format("""
                {
                  "choices": [
                    {
                      "message": {
                        "role": "assistant",
                        "content": %s}}]}""", objectMapper.writeValueAsString(OpenAiConfig.ingredientResponseTest));

        mockServer.expect(requestTo(baseUrl + "/v1/chat/completions"))
                .andExpect(method(org.springframework.http.HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        String result = nutrientOpenAiService.getNutrients("rindehack", "");

        assertNotNull(result);
        assertEquals(OpenAiConfig.ingredientResponseTest, result);

        mockServer.verify();
    }
    @Test
    void getNutrients_shouldThrowResponseStatusException_responseIsEmpty() {
        // given
        String response = "";
        // when
        mockServer.expect(requestTo(baseUrl + "/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        assertThrows(OpenAiNotFoundIngredientException.class, () -> nutrientOpenAiService.getNutrients("Blödsinn", ""));
    }
    @Test
    void getNutrients_shouldThrowResponseStatusException_responseCouldNotParsed() {
        // given
        String response =  "{'choices':[{}}]}";
        // when
        mockServer.expect(requestTo(baseUrl + "/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        assertThrows(OpenAiNotFoundIngredientException.class, () -> nutrientOpenAiService.getNutrients("Blödsinn", ""));
    }
}