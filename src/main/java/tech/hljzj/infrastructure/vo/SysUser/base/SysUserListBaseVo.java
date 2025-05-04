package tech.hljzj.infrastructure.vo.SysUser.base;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysUser;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户管理 sys_user_ 
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysUserListBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id_
     */
    @ExcelProperty(value = "id_")
    private String id;
    /**
     * 所属部门
     */
    @ExcelProperty(value = "所属部门")
    private String deptId;
    /**
     * 部门内身份
     */
    @ExcelProperty(value = "部门内身份")
    private String deptIdentity;
    /**
     * 账号
     */
    @ExcelProperty(value = "账号")
    private String username;
    /**
     * 密码策略
     */
    @ExcelProperty(value = "密码策略")
    private String passwordPolicy;
    /**
     * 现有密码过期时间
     */
    @ExcelProperty(value = "现有密码过期时间")
    private Date passwordExpired;
    /**
     * 昵称
     */
    @ExcelProperty(value = "昵称")
    private String nickname;
    /**
     * 真实姓名
     */
    @ExcelProperty(value = "真实姓名")
    private String realname;
    /**
     * 性别
     */
    @ExcelProperty(value = "性别")
    private String sex;
    /**
     * 证件类型
     */
    @ExcelProperty(value = "证件类型")
    private String cardType;
    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码")
    private String cardNo;
    /**
     * 手机号码
     */
    @ExcelProperty(value = "手机号码")
    private String phone;
    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;
    /**
     * 工作地点
     */
    @ExcelProperty(value = "工作地点")
    private String workAddr;
    /**
     * 工作单位
     */
    @ExcelProperty(value = "工作单位")
    private String workUnit;
    /**
     * 工作职务
     */
    @ExcelProperty(value = "工作职务")
    private String workPos;
    /**
     * 工作职级
     */
    @ExcelProperty(value = "工作职级")
    private String workRank;
    /**
     * 家庭住址
     */
    @ExcelProperty(value = "家庭住址")
    private String homeAddr;
    /**
     * 家庭电话
     */
    @ExcelProperty(value = "家庭电话")
    private String homePhone;
    /**
     * 用户来源
     */
    @ExcelProperty(value = "用户来源")
    private String source;
    /**
     * 排序编号
     */
    @ExcelProperty(value = "排序编号")
    private Integer sort;
    /**
     * 用户状态
     */
    @ExcelProperty(value = "用户状态")
    private String status;
    /**
     * 账户是否锁定
     */
    @ExcelProperty(value = "账户是否锁定")
    private String accountLock;
    /**
     * 上一次修改密码时间（这里配置了一段时间仍然可以使用旧密码来登录）
     */
    @ExcelProperty(value = "上一次修改密码时间（这里配置了一段时间仍然可以使用旧密码来登录）")
    private Date lastChangePassword;

    public <T extends SysUserListBaseVo> T fromDto(SysUser dto){
        this.setId(dto.getId());
        this.setDeptId(dto.getDeptId());
        this.setDeptIdentity(dto.getDeptIdentity());
        this.setUsername(dto.getUsername());
        this.setPasswordPolicy(dto.getPasswordPolicy());
        this.setPasswordExpired(dto.getPasswordExpired());
        this.setNickname(dto.getNickname());
        this.setRealname(dto.getRealname());
        this.setSex(dto.getSex());
        this.setCardType(dto.getCardType());
        this.setCardNo(dto.getCardNo());
        this.setPhone(dto.getPhone());
        this.setEmail(dto.getEmail());
        this.setWorkAddr(dto.getWorkAddr());
        this.setWorkUnit(dto.getWorkUnit());
        this.setWorkPos(dto.getWorkPos());
        this.setWorkRank(dto.getWorkRank());
        this.setHomeAddr(dto.getHomeAddr());
        this.setHomePhone(dto.getHomePhone());
        this.setSource(dto.getSource());
        this.setSort(dto.getSort());
        this.setStatus(dto.getStatus());
        this.setAccountLock(dto.getAccountLock());
        this.setLastChangePassword(dto.getLastChangePassword());
        //noinspection unchecked
        return (T) this;
    }

    public SysUser toDto(){
        SysUser dto = new SysUser();
        dto.setId(this.getId());
        dto.setDeptId(this.getDeptId());
        dto.setDeptIdentity(this.getDeptIdentity());
        dto.setUsername(this.getUsername());
        dto.setPasswordPolicy(this.getPasswordPolicy());
        dto.setPasswordExpired(this.getPasswordExpired());
        dto.setNickname(this.getNickname());
        dto.setRealname(this.getRealname());
        dto.setSex(this.getSex());
        dto.setCardType(this.getCardType());
        dto.setCardNo(this.getCardNo());
        dto.setPhone(this.getPhone());
        dto.setEmail(this.getEmail());
        dto.setWorkAddr(this.getWorkAddr());
        dto.setWorkUnit(this.getWorkUnit());
        dto.setWorkPos(this.getWorkPos());
        dto.setWorkRank(this.getWorkRank());
        dto.setHomeAddr(this.getHomeAddr());
        dto.setHomePhone(this.getHomePhone());
        dto.setSource(this.getSource());
        dto.setSort(this.getSort());
        dto.setStatus(this.getStatus());
        dto.setAccountLock(this.getAccountLock());
        dto.setLastChangePassword(this.getLastChangePassword());
        return dto;
    }
}