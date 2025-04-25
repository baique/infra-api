package tech.hljzj.infrastructure.vo.SysRole;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.vo.SysApp.SysAppListVo;

import java.util.List;

/**
 * 授权给用户的应用和角色信息
 */
@Getter
@Setter
public class GrantAppRoleVo {
    private SysAppListVo appInfo;
    private List<SysRoleListVo> roleList;
}
