package tech.hljzj.infrastructure.config;

import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.hljzj.infrastructure.service.mcp.UserMcpService;

import java.util.List;

@Configuration
public class ToolsConfig {
    @Bean
    public MethodToolCallbackProvider builder(@Autowired List<Mcp> service) {
        return MethodToolCallbackProvider.builder()
            .toolObjects(service.toArray(new Mcp[0]))
            .build();
    }
}
