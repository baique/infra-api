package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.pojo.dto.BizBaseEntity;
import tech.hljzj.framework.service.sort.ISort;


/**
 * 组织管理 sys_dept
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_dept_")
public class SysDept extends BizBaseEntity implements ISort {
    private static final long serialVersionUID = 1L;

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


    /**
     * 从新的实体中更新属性
     */
    public void updateForm(SysDept entity) {
        this.setParentId(entity.getParentId());
        this.setKey(entity.getKey());
        this.setName(entity.getName());
        this.setAlias(entity.getAlias());
        this.setShortName(entity.getShortName());
        this.setType(entity.getType());
        this.setDuty(entity.getDuty());
        this.setUscNo(entity.getUscNo());
        this.setAddr(entity.getAddr());
        this.setAddrNo(entity.getAddrNo());
        this.setStatus(entity.getStatus());
        this.setEnable(entity.getEnable());
        this.setAllowUserJoin(entity.getAllowUserJoin());
        this.setSort(entity.getSort());
        this.setTmp(entity.getTmp());
    }
}