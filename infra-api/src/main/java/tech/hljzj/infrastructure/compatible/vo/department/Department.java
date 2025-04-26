package tech.hljzj.infrastructure.compatible.vo.department;

import lombok.Getter;
import tech.hljzj.framework.util.tree.TreeNode;
import tech.hljzj.infrastructure.domain.SysDept;
import tech.hljzj.infrastructure.vo.SysDept.SysDeptQueryVo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Department implements Serializable, TreeNode<Department> {
    private String departmentId;
    private String departmentName;
    private String departmentCode;
    private String departmentParent;
    private String isOs;
    private String departmentShorName;
    private String regionCode;
    private String invalidFlag;
    private Integer sortNo;
    private String deptType;
    private String deptTypeName;
    private String createUser;
    private Timestamp createTime;
    private String modifyUser;
    private Timestamp modifyTime;
    private Integer istmp;

    public static Department from(SysDept f) {
        Department department = new Department();
        department.setDepartmentId(f.getId());
        department.setDepartmentCode(f.getKey());
        department.setDepartmentName(f.getName());
        department.setDepartmentParent(f.getParentId());
        department.setDeptType(f.getType());
        department.setDeptTypeName(f.getType());
        department.setCreateTime(new Timestamp(f.getCreateTime().getTime()));
        department.setCreateUser(f.getOwnerUserId());
        department.setModifyUser(f.getOwnerUserId());
        department.setModifyTime(new Timestamp(f.getUpdateTime().getTime()));
        //
        try {
            department.setIstmp(Integer.parseInt(f.getTmp()));
        } catch (Exception e) {
            department.setIstmp(0);
        }
        department.setSortNo(f.getSort());
        return department;
    }

    public Integer getIstmp() {
        return istmp;
    }

    public void setIstmp(Integer istmp) {
        this.istmp = istmp;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentParent() {
        return departmentParent;
    }

    public void setDepartmentParent(String departmentParent) {
        this.departmentParent = departmentParent;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getIsOs() {
        return isOs;
    }

    public void setIsOs(String isOs) {
        this.isOs = isOs;
    }

    public String getDepartmentShorName() {
        return departmentShorName;
    }

    public void setDepartmentShorName(String departmentShorName) {
        this.departmentShorName = departmentShorName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getInvalidFlag() {
        return invalidFlag;
    }

    public void setInvalidFlag(String invalidFlag) {
        this.invalidFlag = invalidFlag;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public String getDeptTypeName() {
        return deptTypeName;
    }

    public void setDeptTypeName(String deptTypeName) {
        this.deptTypeName = deptTypeName;
    }

    public SysDeptQueryVo toQuery() {
        Department department = this;
        SysDeptQueryVo queryVo = new SysDeptQueryVo();
        queryVo.setId(department.getDepartmentId());
        queryVo.setKeyLike(department.getDepartmentCode());
        queryVo.setNameLike(department.getDepartmentName());
        queryVo.setShortNameLike(department.getDepartmentShorName());
        queryVo.setParentId(department.getDepartmentParent());
        queryVo.setTypeLike(department.getDeptType());
        if (department.getIstmp() != null) {
            queryVo.setTmp(department.getIstmp().toString());
        }
        return queryVo;
    }

    @Override
    public String getId() {
        return this.departmentId;
    }

    @Override
    public String getParentId() {
        return this.departmentParent;
    }

    @Getter
    private List<Department> departmentChildren;

    @Override
    public void setChildren(List<Department> children) {
        this.departmentChildren = children;
    }

    @Override
    public List<Department> getChildren() {
        return departmentChildren;
    }
}
