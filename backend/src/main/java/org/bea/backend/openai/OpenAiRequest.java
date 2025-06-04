package org.bea.backend.openai;

import java.util.List;

public record OpenAiRequest(
        String model,
        String format,
        double temperature,
        List<OpenAiMessageRequest> messages
) {}
