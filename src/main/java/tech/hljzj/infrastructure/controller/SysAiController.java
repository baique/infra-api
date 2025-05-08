package tech.hljzj.infrastructure.controller;

//import co.elastic.clients.elasticsearch.ml.QuestionAnsweringInferenceOptions;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.cache.CommonCache;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.security.bean.LoginUser;
import tech.hljzj.framework.util.web.AuthUtil;
import tech.hljzj.infrastructure.config.provider.MySqlChatRepository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
//import org.springframework.ai.vectorstore.SearchRequest;
//import org.springframework.ai.vectorstore.VectorStore;
;

@RestController
public class SysAiController extends BaseController {
    @Autowired
    private ChatClient client;
    @Autowired
    private ObjectMapper objectMapper;

    @Getter
    @Setter
    public static class ChatMessage {
        private String role;    // "user" 或 "assistant"
        private String content;
    }

    @Autowired
    private VectorStore vectorStore;
    @Autowired
    private MySqlChatRepository chatRepository;
    @Autowired
    private CommonCache commonCache;

    @PostMapping(value = "/ai/history")
    public R<List<Message>> messages() {
        LoginUser loginUser = AuthUtil.getLoginUser();
        String userId = DigestUtil.md5Hex(loginUser.getAccessToken());
        List<Message> d = chatRepository.findByConversationId(userId);
        return R.ok(d);
    }


    @PostMapping(value = "/ai/clear")
    public R<Void> clear() {
        LoginUser loginUser = AuthUtil.getLoginUser();
        String userId = DigestUtil.md5Hex(loginUser.getAccessToken());
        chatRepository.deleteByConversationId(userId);
        return R.ok();
    }

    @PostMapping(value = "/ai/send")
    public R<String> send(@RequestBody ChatMessage message) {
        String msgId = IdUtil.fastSimpleUUID();
        commonCache.put(msgId, message, 1, TimeUnit.MINUTES);
        return R.ok(msgId);
    }

    @GetMapping(value = "/ai/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestParam String msgId) throws IOException {
        ChatMessage msg;
        Object msgObj = commonCache.get(msgId);
        if (msgObj != null) {
            msg = (ChatMessage) msgObj;
        } else {
            throw UserException.defaultError("消息会话丢失");
        }
        SseEmitter emitter = new SseEmitter(0L);
        LoginUser loginUser = AuthUtil.getLoginUser();
        String userId = DigestUtil.md5Hex(loginUser.getAccessToken());
        response.setHeader("Content-Type", "text/event-stream");
        QuestionAnswerAdvisor qaa = QuestionAnswerAdvisor.builder(vectorStore)
            .promptTemplate(new PromptTemplate("""
                下文提供背景知识
                ---------------------
                {question_answer_context}
                ---------------------
                根据给定的背景知识回答用户问题，遵守以下规则：
                . 使用中文回答
                . 只能从背景知识或者聊天记录中获取答案，如果背景知识或聊天记录中没有，就说我不知道
                . 回答简洁、排版美观
                """))
            .searchRequest(SearchRequest.builder()
                .similarityThreshold(0.1d)
                .topK(6)
                .build())
            .build();
        client.prompt()
            .user(msg.getContent())
            .advisors(a -> a
                .param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, userId)
                .advisors(
                    qaa,
                    new MessageChatMemoryAdvisor(MessageWindowChatMemory.builder()
                        .chatMemoryRepository(chatRepository)
                        .build())
                )
            )
            .stream()
            .chatResponse()
            .map(ChatResponse::getResults)
            .flatMapIterable(list -> list)
            .doOnNext(n -> {
                try {
                    emitter.send(objectMapper.writeValueAsString(n));
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            })
            .subscribe()
        ;
        return emitter;
    }

    @PostMapping("/ai/uploadDocument")
    public Object parseContent(@RequestPart("files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            DocumentReader reader;
            String name = file.getOriginalFilename();
            Assert.notNull(name, "文件名不应为空");
            reader = new TikaDocumentReader(file.getResource());
            List<Document> rawDocs = reader.read();
            for (Document doc : rawDocs) {
                doc.getMetadata().put("source", "knowledge_base");
                doc.getMetadata().put("filename", name);
                doc.getMetadata().put("origin", "user_uploaded_file");
                doc.getMetadata().put("type", "static"); // 明确是静态资料
            }
            TokenTextSplitter splitter = new TokenTextSplitter();
            List<Document> applyDocument = splitter.apply(rawDocs);

            vectorStore.add(applyDocument);
        }
        return R.ok();
    }

}
