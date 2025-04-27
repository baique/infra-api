package tech.hljzj.infrastructure.compatible.vo.role;


import tech.hljzj.infrastructure.compatible.vo.permission.Permission;

import java.io.Serializable;
import java.util.List;

public class RolePermission extends Role implements Serializable {
    private List<Permission> permissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
