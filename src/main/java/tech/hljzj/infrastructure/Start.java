package tech.hljzj.infrastructure;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.hljzj.framework.Bootstrap;

@SpringBootApplication
public class Start extends Bootstrap {
    // 本地启动器
    public static void main(String[] args) {
        start(Start.class, args);
    }
}
