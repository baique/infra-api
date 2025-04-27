package tech.hljzj.infrastructure.domain;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@TableName("v_sys_user_")
public class VSysUser extends SysUser {
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

    @TableField(value = "attribution_", exist = false)
    private String attribution;

    @JsonIgnore
    public Map<String, Object> getAttributionMap() {
        if (StrUtil.isBlank(attribution)) {
            return Collections.emptyMap();
        }
        return JSONUtil.parseObj(attribution);
    }
}
