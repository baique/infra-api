package tech.hljzj.infrastructure.vo.SysRole;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysRole;
import tech.hljzj.infrastructure.vo.SysRole.base.SysRoleListBaseVo;

import java.util.Date;

@Getter
@Setter
public class SysLoginBindRole extends SysRoleListVo {
    /**
     * 来源
     */
    private String source;
    private Date createTime;

    @Override
    public <T extends SysRoleListBaseVo> T fromDto(SysRole dto) {
        this.setCreateTime(dto.getCreateTime());
        return super.fromDto(dto);
    }
}
