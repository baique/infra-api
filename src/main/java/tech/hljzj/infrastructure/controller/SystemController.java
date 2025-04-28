package tech.hljzj.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.security.an.Anonymous;

@RestController
public class SystemController {
    @Value("${app.version}")
    private String version;

    @Anonymous
    @GetMapping("/version")
    @ResponseBody
    public R<String> version() {
        return R.ok(version);
    }
}
