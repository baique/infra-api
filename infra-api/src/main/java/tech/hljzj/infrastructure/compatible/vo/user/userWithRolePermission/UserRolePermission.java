package tech.hljzj.infrastructure.compatible.vo.user.userWithRolePermission;


import tech.hljzj.infrastructure.compatible.vo.permission.Permission;
import tech.hljzj.infrastructure.compatible.vo.role.RolePermission;
import tech.hljzj.infrastructure.compatible.vo.user.userMini.UserMiniInfo;
import tech.hljzj.infrastructure.domain.VSysUser;

import java.io.Serializable;
import java.util.List;

public class UserRolePermission extends UserMiniInfo implements Serializable {
    private List<RolePermission> roles;

    public static UserRolePermission from(VSysUser userInfo) {
        UserRolePermission up = new UserRolePermission();
        up.fromVSysUser(userInfo);
        return up;
    }

    public List<RolePermission> getRoles() {
        return roles;
    }

    public void setRoles(List<RolePermission> roles) {
        this.roles = roles;
    }


    public RolePermission hasRoleByValue(String value) {
        if (this.roles != null) {
            for (RolePermission roleBean : this.roles) {
                if (roleBean.getRoleValue().equals(value)) {
                    return roleBean;
                }
            }
        }
        return null;
    }

    public Permission hasPermissionByValue(String permissionValue) {
        if (this.roles != null) {
            for (RolePermission roleBean : this.roles) {
                if (roleBean.getPermissions() != null) {
                    for (Permission permissionInfo : roleBean.getPermissions()) {
                        if (permissionInfo.getPermissionValue().equals(permissionValue)) {
                            return permissionInfo;
                        }
                    }
                }
            }
        }
        return null;
    }
}
