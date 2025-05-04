package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.pojo.dto.BizBaseEntity;
import tech.hljzj.framework.service.sort.ISort;

import java.util.Date;

/**
 * 用户管理 sys_user_
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_user_")
public class SysUser extends BizBaseEntity implements ISort {
    private static final long serialVersionUID = 1L;

    /**
     * id_
     */
    @TableId(type = IdType.ASSIGN_UUID, value = "id_")
    private String id;
    /**
     * 所属部门
     */
    @TableField(value = "dept_id_")
    private String deptId;
    /**
     * 部门内身份
     */
    @TableField(value = "dept_identity_")
    private String deptIdentity;
    /**
     * 账号
     */
    @TableField(value = "username_")
    private String username;
    /**
     * 密码
     */
    @TableField(value = "password_")
    private String password;
    /**
     * mask_v_
     */
    @TableField(value = "mask_v_")
    private String maskV;
    /**
     * 密码策略
     */
    @TableField(value = "password_policy_")
    private String passwordPolicy;
    /**
     * 现有密码过期时间
     */
    @TableField(value = "password_expired_")
    private Date passwordExpired;
    /**
     * 旧密码
     */
    @TableField(value = "old_password_")
    private String oldPassword;

    /**
     * 昵称
     */
    @TableField(value = "nickname_")
    private String nickname;
    /**
     * 真实姓名
     */
    @TableField(value = "realname_")
    private String realname;
    /**
     * 性别
     */
    @TableField(value = "sex_")
    private String sex;
    /**
     * 证件类型
     */
    @TableField(value = "card_type_")
    private String cardType;
    /**
     * 证件号码
     */
    @TableField(value = "card_no_")
    private String cardNo;
    /**
     * 手机号码
     */
    @TableField(value = "phone_")
    private String phone;
    /**
     * 邮箱
     */
    @TableField(value = "email_")
    private String email;
    /**
     * 工作地点
     */
    @TableField(value = "work_addr_")
    private String workAddr;
    /**
     * 工作单位
     */
    @TableField(value = "work_unit_")
    private String workUnit;
    /**
     * 工作职务
     */
    @TableField(value = "work_pos_")
    private String workPos;
    /**
     * 工作职级
     */
    @TableField(value = "work_rank_")
    private String workRank;
    /**
     * 家庭住址
     */
    @TableField(value = "home_addr_")
    private String homeAddr;
    /**
     * 家庭电话
     */
    @TableField(value = "home_phone_")
    private String homePhone;
    /**
     * 用户来源
     */
    @TableField(value = "source_")
    private String source;
    /**
     * 排序编号
     */
    @TableField(value = "sort_")
    private Integer sort;
    /**
     * 用户状态
     */
    @TableField(value = "status_")
    private String status;
    /**
     * 账户是否锁定
     */
    @TableField(value = "account_lock_")
    private String accountLock;
    /**
     * 上一次修改密码时间（这里配置了一段时间仍然可以使用旧密码来登录）
     */
    @TableField(value = "last_change_password_")
    private Date lastChangePassword;


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysUser entity) {
        this.setDeptId(entity.getDeptId());
        this.setDeptIdentity(entity.getDeptIdentity());
        this.setUsername(entity.getUsername());
        this.setPasswordPolicy(entity.getPasswordPolicy());
        this.setPasswordExpired(entity.getPasswordExpired());
        this.setNickname(entity.getNickname());
        this.setRealname(entity.getRealname());
        this.setSex(entity.getSex());
        this.setCardType(entity.getCardType());
        this.setCardNo(entity.getCardNo());
        this.setPhone(entity.getPhone());
        this.setEmail(entity.getEmail());
        this.setWorkAddr(entity.getWorkAddr());
        this.setWorkUnit(entity.getWorkUnit());
        this.setWorkPos(entity.getWorkPos());
        this.setWorkRank(entity.getWorkRank());
        this.setHomeAddr(entity.getHomeAddr());
        this.setHomePhone(entity.getHomePhone());
        this.setSource(entity.getSource());
        this.setSort(entity.getSort());
        this.setStatus(entity.getStatus());
        this.setAccountLock(entity.getAccountLock());
    }
}