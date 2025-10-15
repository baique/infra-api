package tech.hljzj.infrastructure.config;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;
import tech.hljzj.protect.password.PasswordNotSafeException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LocalSecurityProviderTest {

    @Test
    void validatePasswordLastChangeDate() throws PasswordNotSafeException {
        LocalSecurityProvider.validatePasswordLastChangeDate(
            1,
            DateUtil.parse("2025年10月14日 09:13:00")
        );
        System.out.println("密码正确");
    }

    @Test
    void validateBindIp() {
        LocalSecurityProvider localSecurityProvider = new LocalSecurityProvider();
        localSecurityProvider.validateBindIp("张三", Arrays.asList(
            "127.0.0.1"
        ));
    }
}