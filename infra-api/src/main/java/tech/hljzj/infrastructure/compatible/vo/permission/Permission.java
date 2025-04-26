package tech.hljzj.infrastructure.compatible.vo.permission;

import lombok.Getter;
import tech.hljzj.framework.util.tree.TreeNode;
import tech.hljzj.infrastructure.domain.SysMenu;
import tech.hljzj.infrastructure.vo.SysMenu.SysMenuQueryVo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Permission implements Serializable, TreeNode<Permission> {

    private String permissionId;
    private String permissionName;
    private String permissionValue;
    private String permissionDesc;
    private Timestamp createTime;
    private String programId;
    private String permissionTag;
    private String permissionClass;
    private Integer permissionSort;
    private String permissionParent;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getPermissionTag() {
        return permissionTag;
    }

    public void setPermissionTag(String permissionTag) {
        this.permissionTag = permissionTag;
    }

    public String getPermissionClass() {
        return permissionClass;
    }

    public void setPermissionClass(String permissionClass) {
        this.permissionClass = permissionClass;
    }

    public Integer getPermissionSort() {
        return permissionSort;
    }

    public void setPermissionSort(Integer permissionSort) {
        this.permissionSort = permissionSort;
    }

    public String getPermissionParent() {
        return permissionParent;
    }

    public void setPermissionParent(String permissionParent) {
        this.permissionParent = permissionParent;
    }


    public SysMenuQueryVo toQuery() {
        SysMenuQueryVo query = new SysMenuQueryVo();
        query.setId(permissionId);
        query.setKey(permissionValue);
        query.setNameLike(permissionName);
        query.setDescLike(permissionDesc);
        query.setTagLike(permissionTag);
        query.setParentIdLike(permissionParent);
        query.setClassesLike(permissionClass);
        return query;
    }

    public static Permission from(SysMenu sysMenu) {
        if (sysMenu == null) {
            return null;
        }
        Permission p = new Permission();
        p.setPermissionId(sysMenu.getId());
        p.setPermissionName(sysMenu.getName());
        p.setPermissionValue(sysMenu.getKey());
        p.setPermissionDesc(sysMenu.getDesc());
        p.setCreateTime(new Timestamp(sysMenu.getCreateTime().getTime()));
        p.setProgramId(sysMenu.getOwnerAppId());
        p.setPermissionTag(sysMenu.getTag());
        p.setPermissionClass(sysMenu.getClasses());
        p.setPermissionSort(sysMenu.getSort());
        p.setPermissionParent(sysMenu.getParentId());
        return p;
    }

    @Override
    public String getId() {
        return this.permissionId;
    }

    @Override
    public String getParentId() {
        return this.permissionParent;
    }

    @Getter
    private List<Permission> permissionTreeList;

    @Override
    public void setChildren(List<Permission> children) {
        this.permissionTreeList = children;
    }

    @Override
    public List<Permission> getChildren() {
        return this.permissionTreeList;
    }
}
