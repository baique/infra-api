package tech.hljzj.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.security.bean.TokenAuthentication;
import tech.hljzj.framework.util.web.AuthUtil;
import tech.hljzj.infrastructure.config.TokenAuthenticateService;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenAuthenticateService authenticateService;

    @PostMapping("/token")
    public R<TokenAuthentication> token() throws Exception {
        TokenAuthentication authentication = AuthUtil.getAuthentication();
        return R.ok(authenticateService.authenticate(authentication.getToken()));
    }
}
