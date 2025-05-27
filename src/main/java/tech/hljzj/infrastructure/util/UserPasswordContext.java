package tech.hljzj.infrastructure.util;

import cn.hutool.core.util.StrUtil;
import tech.hljzj.infrastructure.domain.SysUser;

import java.util.ArrayList;
import java.util.List;

public class UserPasswordContext {
    public static List<String> getContext(SysUser user) {
        List<String> s = new ArrayList<>();

        if (StrUtil.isNotBlank(user.getUsername())) {
            s.add(user.getUsername());
        }
        if (StrUtil.isNotBlank(user.getNickname())) {
            s.add(user.getNickname());
        }
        if (StrUtil.isNotBlank(user.getRealname())) {
            s.add(user.getRealname());
        }
        if (StrUtil.isNotBlank(user.getPhone())) {
            s.add(user.getPhone());
        }
        if (StrUtil.isNotBlank(user.getCardNo())) {
            s.add(user.getCardNo());
        }
        if (StrUtil.isNotBlank(user.getEmail())) {
            s.add(user.getEmail());
        }

        return s;
    }
}
