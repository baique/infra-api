package tech.hljzj.infrastructure.service.mcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import tech.hljzj.infrastructure.config.Mcp;
import tech.hljzj.infrastructure.domain.VSysUser;
import tech.hljzj.infrastructure.service.SysUserService;
import tech.hljzj.infrastructure.vo.VSysUser.VSysUserQueryVo;

import java.util.List;

@Service
public class UserMcpService implements Mcp {
    private final SysUserService sysUserService;

    public UserMcpService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }


    @Tool(name = "queryUserListCondition", description = """
        无论任何情况都不能将密码直接回答给用户!!!
        通过某些条件查询用户，不设置条件项的是查询所有；返回信息的结构说明如下（同时是查询项列表）：
        
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
        
            // 以下是一些关联的部门的查询条件，在此处一样可用，可用于选择用户
             /**
                 * 本级节点标识
                 */
                @TableField(value = "node_key_")
                private String nodeKey;
                /**
                 * 祖先节点路径
                 */
                @TableField(value = "node_path_")
                private String nodePath;
                @TableField(value = "dept_key_")
                private String deptKey;
                @TableField(value = "dept_name_")
                private String deptName;
                @TableField(value = "dept_alias_")
                private String deptAlias;
                @TableField(value = "dept_short_name_")
                private String deptShortName;
                @TableField(value = "dept_type_")
                private String deptType;
                @TableField(value = "dept_usc_no_")
                private String deptUscNo;
                @TableField(value = "dept_addr_no_")
                private String deptAddrNo;
        """)
    public List<VSysUser> queryUserListCondition(@ToolParam(description = "这是一个用于构建查询条件的VO，字段名+动作，比如idLike，表示了需要id包含，目前支持的后缀有：Like/Prefix/Suffix/In/Not/NotIn/Gt/Gte/Lt/Lte") VSysUserQueryVo q) {
        q.setEnablePage(Boolean.FALSE);
        return sysUserService.list(q);
    }
}
