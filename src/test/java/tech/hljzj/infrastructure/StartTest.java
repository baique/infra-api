package tech.hljzj.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import tech.hljzj.framework.test.AppTest;

@AppTest
@Slf4j
public class StartTest {
    @Test
    void applicationRunnerTest() {
        log.info("本应用已通过启动验证，可以正常启动");
    }
}
