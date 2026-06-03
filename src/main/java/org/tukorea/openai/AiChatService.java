package org.tukorea.openai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AiChatService {

    @Autowired
    private ChatClient chatClient;

    public String chatResponse(String message) {
        String response = chatClient.prompt()
                .user(message)
                .call()
                .content();

        return response;
    }

    public Flux<String> chatStream(String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .chatResponse()
                .map(response -> {
                    if (response.getResult() == null) {
                        return "";
                    }

                    String content = response.getResult().getOutput().getText();
                    return content != null ? content : "";
                });
    }
}