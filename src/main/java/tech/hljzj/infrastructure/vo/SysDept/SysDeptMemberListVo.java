package tech.hljzj.infrastructure.vo.SysDept;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.domain.VSysDeptMemberUser;
import tech.hljzj.infrastructure.vo.SysUser.SysUserListVo;
import tech.hljzj.infrastructure.vo.SysUser.base.SysUserListBaseVo;

@Getter
@Setter
public class SysDeptMemberListVo extends SysUserListVo {
    /**
     * 部门从属关系：1，主部门，2，兼属部门
     */
    private Integer ownerType;

    @Override
    public <T extends SysUserListBaseVo> T fromDto(SysUser dto) {
        if (dto instanceof VSysDeptMemberUser) {
            VSysDeptMemberUser po = (VSysDeptMemberUser) dto;
            this.ownerType = (po.getOwnerType());
        }
        return super.fromDto(dto);
    }
}
