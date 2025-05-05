package tech.hljzj.infrastructure.service.mcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import tech.hljzj.infrastructure.config.Mcp;
import tech.hljzj.infrastructure.domain.SysDept;
import tech.hljzj.infrastructure.service.SysDeptService;
import tech.hljzj.infrastructure.vo.SysDept.SysDeptQueryVo;

import java.util.List;

@Service
public class DeptMcpService implements Mcp {
    private final SysDeptService sysDeptService;

    public DeptMcpService(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }


    @Tool(name = "queryDeptListCondition", description = """
        组织或者叫：部门、机构、单位
        用户属于某个组织，一个组织下可以有很多用户。
        通过某些条件查询组织数据，不设置条件项的是查询所有；返回信息的结构说明如下（同时是查询项列表）：
        /**
             * bigint
             */
            @TableId(type = IdType.ASSIGN_UUID, value = "id_")
            private String id;
            /**
             * 上级组织
             */
            @TableField(value = "parent_id_")
            private String parentId;
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
            /**
             * 组织标识
             */
            @TableField(value = "key_")
            private String key;
            /**
             * 组织名称
             */
            @TableField(value = "name_")
            private String name;
            /**
             * 组织别名
             */
            @TableField(value = "alias_")
            private String alias;
            /**
             * 组织简称
             */
            @TableField(value = "short_name_")
            private String shortName;
            /**
             * 组织类型
             */
            @TableField(value = "type_")
            private String type;
            /**
             * 组织责任说明
             */
            @TableField(value = "duty_")
            private String duty;
            /**
             * 统一社会信用代码
             */
            @TableField(value = "usc_no_")
            private String uscNo;
            /**
             * 所在地点
             */
            @TableField(value = "addr_")
            private String addr;
            /**
             * 所在地点编码
             */
            @TableField(value = "addr_no_")
            private String addrNo;
            /**
             * 状态
             */
            @TableField(value = "status_")
            private String status;
            /**
             * 是否启用
             */
            @TableField(value = "enable_")
            private String enable;
            /**
             * 允许用户加入
             */
            @TableField(value = "allow_user_join_")
            private String allowUserJoin;
            /**
             * 排序编号
             */
            @TableField(value = "sort_")
            private Integer sort;
            /**
             * 是否临时组织
             */
            @TableField(value = "tmp_")
            private String tmp;
        """)
    public List<SysDept> queryDeptListCondition(@ToolParam(description = "这是一个用于构建查询条件的VO，字段名+动作，比如idLike，表示了需要id包含，目前支持的后缀有：Like/Prefix/Suffix/In/Not/NotIn/Gt/Gte/Lt/Lte") SysDeptQueryVo q) {
        q.setEnablePage(Boolean.FALSE);
        return sysDeptService.list(q);
    }
}
