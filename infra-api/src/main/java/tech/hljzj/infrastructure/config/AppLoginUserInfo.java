package tech.hljzj.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.security.bean.UserInfo;

@Getter
@Setter
public class AppLoginUserInfo extends UserInfo {
    private String loginAppId;
}
