package org.tukorea.openai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

@Service
public class AiVisionService {

    @Autowired
    private ChatClient chatClient;

    public String imageAnalysis(String question, String contentType, byte[] bytes) {
        MimeType mimeType = MimeType.valueOf(contentType);
        ByteArrayResource resource = new ByteArrayResource(bytes);

        return chatClient.prompt()
                .system("""
                        당신은 이미지 분석 전문가입니다.
                        사용자 질문에 맞게 이미지를 분석하고 답변을 한국어로 하세요.
                        답변은 마크다운 형식을 사용하여 가독성 좋게 정리해주세요.
                        """)
                .user(userSpec -> userSpec
                        .text(question)
                        .media(mimeType, resource)
                )
                .call()
                .content();
    }
}