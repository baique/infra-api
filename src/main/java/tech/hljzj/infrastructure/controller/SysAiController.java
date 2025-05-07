package tech.hljzj.infrastructure.controller;

//import co.elastic.clients.elasticsearch.ml.QuestionAnsweringInferenceOptions;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HtmlUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.tika.Tika;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.security.bean.LoginUser;
import tech.hljzj.framework.util.web.AuthUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

//import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
//import org.springframework.ai.vectorstore.SearchRequest;
//import org.springframework.ai.vectorstore.VectorStore;
;

@RestController
public class SysAiController {
    @Autowired
    private ChatClient client;

    @Getter
    @Setter
    public static class ChatMessage {
        private String role;    // "user" 或 "assistant"
        private String content;
    }

    @Autowired
    private VectorStore vectorStore;

    @PostMapping(value = "/ai/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Generation> stream(@RequestBody List<ChatMessage> messages) {

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
        LoginUser loginUser = AuthUtil.getLoginUser();

        Prompt prompt = new Prompt(promptMessages);
        return client.prompt(prompt)
            .advisors(a -> a.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, DigestUtil.md5(loginUser.getAccessToken())))
            .stream()
            .chatResponse()
            .map(ChatResponse::getResults)
            .flatMapIterable(list -> list)
            ;

    }


    @PostMapping("/ai/uploadDocument")
    public Object parseContent(@RequestPart("file") MultipartFile file) throws Exception {
        Tika tika = new Tika();
        String fileBody;
        byte[] bytes = file.getBytes();
        try (InputStream stream = new ByteArrayInputStream(bytes)) {
            fileBody = tika.parseToString(stream);
        }
        String[] split = fileBody.split("\n");
        StringJoiner body = new StringJoiner("\n");
        for (String s : split) {
            if (StrUtil.isNotBlank(s)) {
                body.add(s);
            }
        }
        fileBody = body.toString();

        //去除所有html标签
        fileBody = HtmlUtil.cleanHtmlTag(fileBody);
        if (StrUtil.isBlank(fileBody)) {
            throw new Exception("无法识别的文件内容");
        }
        vectorStore.add(Collections.singletonList(new Document(fileBody)));
        return R.ok();
    }

}
