package org.bea.backend.openai;

public record OpenAiMessageRequest(
        String role,
        String content
) {
}
