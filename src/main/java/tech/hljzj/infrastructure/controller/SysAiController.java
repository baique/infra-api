package tech.hljzj.infrastructure.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import tech.hljzj.framework.security.an.Anonymous;

import java.util.List;
import java.util.stream.Collectors;

;

@RestController
public class SysAiController {
    @Autowired
    private ChatClient client;

    @PostMapping("/ai/message")
    @Anonymous
    public Object message(@RequestBody List<ChatMessage> messages) {
        // 1. DTO → Spring AI Message
        List<Message> promptMessages = messages.stream()
            .map(m -> {
                if ("user".equalsIgnoreCase(m.getRole())) {
                    return new UserMessage(m.getContent());
                } else {
                    return new AssistantMessage(m.getContent());
                }
            })
            .collect(Collectors.toList());
        return client.prompt(new Prompt(promptMessages))
            .call()
            .content();
    }

    @Getter
    @Setter
    public static class ChatMessage {
        private String role;    // "user" 或 "assistant"
        private String content;
    }

    @PostMapping(value = "/ai/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AssistantMessage> stream(@RequestBody List<ChatMessage> messages) {
        SseEmitter emitter = new SseEmitter(0L); // 不超时
        // 1. DTO → Spring AI Message
        List<Message> promptMessages = messages.stream()
            .map(m -> {
                if ("user".equalsIgnoreCase(m.getRole())) {
                    return new UserMessage(m.getContent());
                } else {
                    return new AssistantMessage(m.getContent());
                }
            })
            .collect(Collectors.toList());
        Prompt prompt = new Prompt(promptMessages);
        return client.prompt(prompt).stream().chatResponse()
            .map(ChatResponse::getResults)
            .flatMapIterable(list -> list)
            .map(content -> content.getOutput())
            ;
    }

}
