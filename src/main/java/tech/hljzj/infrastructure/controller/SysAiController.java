package tech.hljzj.infrastructure.controller;

//import co.elastic.clients.elasticsearch.ml.QuestionAnsweringInferenceOptions;

import cn.hutool.crypto.digest.DigestUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.security.bean.LoginUser;
import tech.hljzj.framework.util.web.AuthUtil;
import tech.hljzj.infrastructure.config.provider.MySqlChatRepository;

import java.util.List;

//import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
//import org.springframework.ai.vectorstore.SearchRequest;
//import org.springframework.ai.vectorstore.VectorStore;
;

@RestController
public class SysAiController extends BaseController {
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
    @Autowired
    private MySqlChatRepository chatRepository;

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

    @PostMapping(value = "/ai/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Generation> stream(@RequestBody ChatMessage message) {
        LoginUser loginUser = AuthUtil.getLoginUser();
        String userId = DigestUtil.md5Hex(loginUser.getAccessToken());
        response.setHeader("Content-Type", "text/event-stream");
        QuestionAnswerAdvisor qaa = QuestionAnswerAdvisor.builder(vectorStore)
            .promptTemplate(new PromptTemplate("""
                Context information is below.
                
                ---------------------
                {question_answer_context}
                ---------------------
                
                Given the context information and no prior knowledge, answer the query.
                
                Follow these rules:
                
                1. If the answer is not in the context, just say that you don't know.
                2. Avoid statements like "Based on the context..." or "The provided information...".
                3. 使用中文回答.
                """))
            .searchRequest(SearchRequest.builder()
                .similarityThreshold(0.1d)
                .topK(6)
                .build())
            .build();
        return client.prompt()
            .user(message.getContent())
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
            ;

    }

    @PostMapping("/ai/uploadDocument")
    public Object parseContent(@RequestPart("files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            DocumentReader reader;
            String name = file.getOriginalFilename();
            Assert.notNull(name, "文件名不应为空");
            if (name.endsWith(".pdf")) {
                reader = new ParagraphPdfDocumentReader(file.getResource(),
                    PdfDocumentReaderConfig.builder()
                        .withPageTopMargin(0)
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                            .withNumberOfTopTextLinesToDelete(0)
                            .build())
                        .withPagesPerDocument(1)
                        .build());
            } else {
                reader = new TikaDocumentReader(file.getResource());
            }
            List<Document> rd = reader.read();

            TokenTextSplitter splitter = new TokenTextSplitter();
            List<Document> applyDocument = splitter.apply(rd);

            vectorStore.add(applyDocument);
        }
        return R.ok();
    }

}
