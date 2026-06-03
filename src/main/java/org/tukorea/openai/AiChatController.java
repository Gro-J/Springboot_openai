package org.tukorea.openai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/aichat")
public class AiChatController {

    @Autowired
    private AiChatService aiChatService;

    @GetMapping("/")
    public String chatPage(@RequestParam String stream) {
        if (stream.equals("yes")) {
            return "streamchat";
        } else {
            return "chat";
        }
    }

    @PostMapping("/chat")
    @ResponseBody
    public String getChatResponse(@RequestParam String message) {
        return aiChatService.chatResponse(message);
    }

    @GetMapping(value = "/streamchat", produces = "text/event-stream")
    @ResponseBody
    public Flux<String> getChatStream(@RequestParam String message) {
        return aiChatService.chatStream(message);
    }
}