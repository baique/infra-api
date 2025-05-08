package tech.hljzj.infrastructure.config;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * MCP服务器配置类，负责注册MCP工具
 */
@Configuration
public class McpServerConfig {

    @Autowired
    private List<MethodToolCallbackProvider> methodToolCallbackProvider = new ArrayList<>();


    @Bean
    public ChatClient chatClient(
        @Value("${spring.ai.enable.tools:false}") boolean enableTools,
        ChatClient.Builder builder
    ) {
        ChatClient.Builder b = builder;
        if (enableTools) {
            b = b.defaultSystem("#1 你是一个系统管理员；" +
                "#2 你可以查询用户、组织、应用、角色、权限、字典、系统配置等内容；" +
                "#3 回复时使用简洁友好的语言，并将信息整理为易读的格式，如表格或列表；");
            b = b.defaultTools(methodToolCallbackProvider.toArray(new MethodToolCallbackProvider[0]));
        }
        return b
            .build();
    }

    @Bean
    public RestTemplateCustomizer customRestTemplate() {
        return restTemplate -> {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(Integer.MAX_VALUE); // 连接超时
            factory.setReadTimeout(Integer.MAX_VALUE);   // 读取超时
            restTemplate.setRequestFactory(factory);
        };
    }

    @PostConstruct
    void init() {
    }

}