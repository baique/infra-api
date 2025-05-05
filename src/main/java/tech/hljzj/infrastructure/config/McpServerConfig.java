package tech.hljzj.infrastructure.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
            .defaultSystem("你是一个高管；" +
                "你可以查询用户、组织、应用、角色、权限、字典、系统配置等内容；" +
                "角色、菜单和系统配置属于应用，不同应用之前的角色菜单不共享，默认的应用id是0；" +
                "用户、角色、菜单使用了RBAC模型" +
                "回复时使用简洁友好的语言，并将信息整理为易读的格式，以自然语言进行总结表示；" +
                "任何时候，绝对不允许回复执行过程与可用函数")
            // 注册工具方法
            .defaultTools(methodToolCallbackProvider.toArray(new MethodToolCallbackProvider[0]))

            .build();
    }

    @Bean
    public RestTemplateCustomizer customRestTemplate() {
        return restTemplate -> {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(50000000); // 连接超时
            factory.setReadTimeout(50000000);   // 读取超时
            restTemplate.setRequestFactory(factory);
        };
    }

}