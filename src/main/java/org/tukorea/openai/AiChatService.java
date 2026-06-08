package org.tukorea.openai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AiChatService {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatMemory chatMemory;

    public String chatResponse(String message, String sessionId) {
        MessageChatMemoryAdvisor memoryAdvisor =
                MessageChatMemoryAdvisor.builder(chatMemory).build();

        String response = chatClient.prompt()
                .user(message)
                .advisors(memoryAdvisor)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, sessionId))
                .call()
                .content();

        return response;
    }

    public Flux<String> chatStream(String message, String sessionId) {
        MessageChatMemoryAdvisor memoryAdvisor =
                MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chatClient.prompt()
                .user(message)
                .advisors(memoryAdvisor)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, sessionId))
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