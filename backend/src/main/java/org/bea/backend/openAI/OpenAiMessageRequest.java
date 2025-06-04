package org.bea.backend.openAI;

public record OpenAiMessageRequest(
        String role,
        String content
) {
}
