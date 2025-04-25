package tech.hljzj.compatible.tyrz.vo.role;

import tech.hljzj.infrastructure.domain.SysRole;
import tech.hljzj.infrastructure.vo.SysRole.SysRoleQueryVo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Role implements Serializable {
    private String roleId;
    private String roleName;
    private String roleValue;
    private String roleDesc;
    private Timestamp createTime;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public SysRoleQueryVo toQuery() {
        SysRoleQueryVo rq = new SysRoleQueryVo();
        rq.setId(roleId);
        rq.setKeyLike(roleValue);
        rq.setNameLike(roleName);
        rq.setDescLike(roleDesc);
        return rq;
    }

    public static Role from(SysRole sysRole) {
        Role r = new Role();
        r.setRoleId(sysRole.getId());
        r.setRoleName(sysRole.getName());
        r.setRoleDesc(sysRole.getDesc());
        r.setRoleValue(sysRole.getKey());
        r.setCreateTime(new Timestamp(sysRole.getCreateTime().getTime()));
        return r;
    }
}
