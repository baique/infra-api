package tech.hljzj.infrastructure;


import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.hljzj.framework.Bootstrap;

@SpringBootApplication(exclude = {
    SecurityAutoConfiguration.class,
})
public class Start extends Bootstrap {
    
}
