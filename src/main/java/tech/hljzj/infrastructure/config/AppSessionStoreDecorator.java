package tech.hljzj.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.security.SessionStoreDecorator;

@Primary
@Component
@Slf4j
public class AppSessionStoreDecorator extends SessionStoreDecorator {
}
