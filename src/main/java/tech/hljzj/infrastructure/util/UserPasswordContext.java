package tech.hljzj.infrastructure.util;

import tech.hljzj.infrastructure.domain.SysUser;

import java.util.List;

public class UserPasswordContext {
    public static List<String> getContext(SysUser user) {
        return List.of(
            user.getUsername(),
            user.getNickname(),
            user.getRealname(),
            user.getPhone(),
            user.getCardNo(),
            user.getEmail()
        );
    }
}
