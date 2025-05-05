package tech.hljzj.infrastructure.service.mcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import tech.hljzj.infrastructure.config.Mcp;
import tech.hljzj.infrastructure.domain.SysDictData;
import tech.hljzj.infrastructure.domain.SysDictType;
import tech.hljzj.infrastructure.service.SysDictDataService;
import tech.hljzj.infrastructure.service.SysDictTypeService;
import tech.hljzj.infrastructure.vo.SysDictData.SysDictDataQueryVo;
import tech.hljzj.infrastructure.vo.SysDictType.SysDictTypeQueryVo;

import java.util.List;

@Service
public class DictMcpService implements Mcp {

    private final SysDictDataService sysDictDataService;
    private final SysDictTypeService sysDictTypeService;

    public DictMcpService(SysDictDataService sysDictDataService, SysDictTypeService sysDictTypeService) {
        this.sysDictDataService = sysDictDataService;
        this.sysDictTypeService = sysDictTypeService;
    }

    @Tool(name = "queryDictTypeList", description = """
        字典项，也叫字典分类、字典类别、字典种类等
        每个字典项下都可能有多个字典值
        通过某些条件查询字典项，不设置条件项的是查询所有；返回信息的结构说明如下（同时是查询项列表）：
        /**
             * bigint
             */
            @TableId(type = IdType.ASSIGN_UUID, value = "id_")
            private String id;
            /**
             * 字典类型标识
             */
            @TableField(value = "key_")
            private String key;
            /**
             * 字典类型名称
             */
            @TableField(value = "name_")
            private String name;
            /**
             * 字典类型状态
             */
            @TableField(value = "status_")
            private String status;
            /**
             * 值长度限制
             */
            @TableField(value = "max_length_")
            private Integer maxLength;
            /**
             * 字典说明
             */
            @TableField(value = "help_message_")
            private String helpMessage;
            /**
             * 字典类型描述
             */
            @TableField(value = "desc_")
            private String desc;
            /**
             * 排序编号
             */
            @TableField(value = "sort_")
            private Integer sort;
        """)
    public List<SysDictType> queryDictList(@ToolParam(description = "这是一个用于构建查询条件的VO，字段名+动作，比如idLike，表示了需要id包含，目前支持的后缀有：Like/Prefix/Suffix/In/Not/NotIn/Gt/Gte/Lt/Lte")
                                           SysDictTypeQueryVo q) {
        q.setEnablePage(false);
//        q.setUsername(username);
        return sysDictTypeService.list(q);
    }

    @Tool(name = "queryDictDataList", description = """
            每个字典值只属于一个字典项
            通过某些条件查询字典值，不设置条件项的是查询所有；返回信息的结构说明如下（同时是查询项列表）：
                /**
                 * 所属字典类型标识
                 */
                @TableField(value = "owner_type_id_")
                private String ownerTypeId;
                    /**
                 * 是否锁定
                 */
                @TableField(value = "locked_")
                private String locked;
                /**
                 * 字典标识
                 */
                @TableField(value = "key_")
                private String key;
                /**
                 * 字典名称
                 */
                @TableField(value = "name_")
                private String name;
                /**
                 * 字典项值
                 */
                @TableField(value = "value_")
                private String value;
                /**
                 * 数据样式
                 */
                @TableField(value = "list_class_")
                private String listClass;
                /**
                 * 是否可选
                 */
                @TableField(value = "selectable_")
                private String selectable;
                /**
                 * 是否锁定
                 */
                @TableField(value = "locked_")
                private String locked;
                /**
                 * 字典状态
                 */
                @TableField(value = "status_")
                private String status;
                /**
                 * 帮助说明
                 */
                @TableField(value = "help_message_")
                private String helpMessage;
                /**
                 * 字典描述
                 */
                @TableField(value = "desc_")
                private String desc;
                /**
                 * 排序编号
                 */
                @TableField(value = "sort_")
                private Integer sort;
        """)
    public List<SysDictData> queryDictDataList() {
        SysDictDataQueryVo q = new SysDictDataQueryVo();
        q.setEnablePage(false);
//        q.setUsername(username);
        return sysDictDataService.list(q);
    }
}
