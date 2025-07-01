package tech.hljzj.infrastructure.vo.SysMenu;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysRole;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleDetailVo;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SysMenuGrantedVo {
    private List<SysRoleDetailVo> grantedRoles = new ArrayList<>();
    private SysMenuDetailVo menuDetail;

    public void addRole(SysRole role) {
        this.grantedRoles.add(new SysRoleDetailVo().fromDto(role));
    }
}
