package tech.hljzj.infrastructure.vo.SysUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.domain.VSysUser;
import tech.hljzj.infrastructure.vo.SysUser.base.SysUserListBaseVo;

import java.util.Map;

/**
 * 用户管理 sys_user_
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
@JsonIgnoreProperties({"password", "oldPassword"})
public class SysUserListVo extends SysUserListBaseVo {
    // 在这里可以扩展其他属性
    private String deptKey;
    private String deptName;
    private String deptAlias;
    private String deptShortName;
    private String deptType;
    private String deptUscNo;
    private String deptAddrNo;
    private Map<String, Object> attribution;

    @Override
    public <T extends SysUserListBaseVo> T fromDto(SysUser dto) {
        if (dto instanceof VSysUser) {
            VSysUser po = (VSysUser) dto;
            this.setDeptKey(po.getDeptKey());
            this.setDeptName(po.getDeptName());
            this.setDeptAlias(po.getDeptAlias());
            this.setDeptShortName(po.getDeptShortName());
            this.setDeptType(po.getDeptType());
            this.setDeptUscNo(po.getDeptUscNo());
            this.setDeptAddrNo(po.getDeptAddrNo());
            this.setAttribution(po.getAttributionMap());
        }
        return super.fromDto(dto);
    }
}