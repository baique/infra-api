package tech.hljzj.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.security.bean.TokenAuthentication;
import tech.hljzj.framework.security.service.ITokenAuthenticateService;
import tech.hljzj.framework.util.web.AuthUtil;

@RestController
public class TokenController {
    @Autowired
    private ITokenAuthenticateService authenticateService;

    @PostMapping("/token")
    public R<TokenAuthentication> token() throws Exception {
        TokenAuthentication authentication = AuthUtil.getAuthentication();
        return R.ok(authenticateService.authenticate(authentication.getToken()));
    }
}
