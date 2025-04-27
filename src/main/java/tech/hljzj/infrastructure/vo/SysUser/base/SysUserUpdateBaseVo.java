package tech.hljzj.infrastructure.vo.SysUser.base;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.lang.*;
import java.util.Date;
import tech.hljzj.infrastructure.domain.SysUser;

/**
 * 用户管理 sys_user_ 
 * 交互实体 用于更新
 *
 * @author wa
 */
@Getter
@Setter
public class SysUserUpdateBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id_
     */
    private String id;
    /**
     * 所属部门
     */
    private String deptId;
    /**
     * 部门内身份
     */
    private String deptIdentity;
    /**
     * 账号
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 性别
     */
    private String sex;
    /**
     * 证件类型
     */
    private String cardType;
    /**
     * 证件号码
     */
    private String cardNo;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 工作地点
     */
    private String workAddr;
    /**
     * 工作单位
     */
    private String workUnit;
    /**
     * 工作职务
     */
    private String workPos;
    /**
     * 工作职级
     */
    private String workRank;
    /**
     * 家庭住址
     */
    private String homeAddr;
    /**
     * 家庭电话
     */
    private String homePhone;
    /**
     * 用户来源
     */
    private String source;
    /**
     * 排序编号
     */
    private Integer sort;
    /**
     * 用户状态
     */
    private String status;
    /**
     * 账户是否锁定
     */
    private String accountLock;

    /**
     * 转换为实际的数据实体
     */
    public SysUser toDto(){
        SysUser dto = new SysUser();
          
        dto.setId(this.getId());
        dto.setDeptId(this.getDeptId());
        dto.setDeptIdentity(this.getDeptIdentity());
        dto.setUsername(this.getUsername());
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

        return dto;
    }

}