package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("v_sys_dept_member_user_")
public class VSysDeptMemberUser extends VSysUser {
    @TableField(value = "owner_type")
    private Integer ownerType;
}
