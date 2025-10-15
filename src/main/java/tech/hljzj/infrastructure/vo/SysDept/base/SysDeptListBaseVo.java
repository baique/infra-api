package tech.hljzj.infrastructure.vo.SysDept.base;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.util.excel.dict.DictConvertHandle;
import tech.hljzj.framework.util.excel.dict.UseDict;
import tech.hljzj.infrastructure.domain.SysDept;
import tech.hljzj.infrastructure.vo.SysUser.convert.DeptDictLoader;

import java.io.Serializable;


/**
 * 组织机构 sys_dept_ 
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptListBaseVo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    /**
     * id_
     */
//    @ExcelProperty(value = "id_")
    private String id;
    /**
     * 上级组织
     */
    @ExcelProperty(value = "上级组织", converter = DictConvertHandle.class)
    @UseDict(value = "dept", loader = DeptDictLoader.class)
    private String parentId;
    /**
     * 本级节点标识
     */
//    @ExcelProperty(value = "本级节点标识")
    private String nodeKey;
    /**
     * 祖先节点路径
     */
//    @ExcelProperty(value = "祖先节点路径")
    private String nodePath;
    /**
     * 组织标识
     */
    @ExcelProperty(value = "组织标识")
    private String key;
    /**
     * 组织名称
     */
    @ExcelProperty(value = "组织名称")
    private String name;
    /**
     * 组织别名
     */
    @ExcelProperty(value = "组织别名")
    private String alias;
    /**
     * 组织简称
     */
    @ExcelProperty(value = "组织简称")
    private String shortName;
    /**
     * 组织类型
     */
    @ExcelProperty(value = "组织类型", converter = DictConvertHandle.class)
    @UseDict("org_type")
    private String type;
    /**
     * 组织责任说明
     */
    @ExcelProperty(value = "组织职责说明")
    private String duty;
    /**
     * 统一社会信用代码
     */
    @ExcelProperty(value = "统一社会信用代码")
    private String uscNo;
    /**
     * 所在地点
     */
    @ExcelProperty(value = "所在地点")
    private String addr;
    /**
     * 所在地点编码
     */
    @ExcelProperty(value = "所在地点编码")
    private String addrNo;
    /**
     * 状态
     */
//    @ExcelProperty(value = "状态")
    private String status;
    /**
     * 是否启用
     */
//    @ExcelProperty(value = "是否启用")
    private String enable;
    /**
     * 允许用户加入
     */
//    @ExcelProperty(value = "允许用户加入")
    private String allowUserJoin;
    /**
     * 排序编号
     */
    @ExcelProperty(value = "排序编号")
    private Integer sort;
    /**
     * 是否临时组织
     */
    @ExcelProperty(value = "是否临时组织")
    private String tmp;

    public <T extends SysDeptListBaseVo> T fromDto(SysDept dto) {
        this.setId(dto.getId());
        this.setParentId(dto.getParentId());
        this.setNodeKey(dto.getNodeKey());
        this.setNodePath(dto.getNodePath());
        this.setKey(dto.getKey());
        this.setName(dto.getName());
        this.setAlias(dto.getAlias());
        this.setShortName(dto.getShortName());
        this.setType(dto.getType());
        this.setDuty(dto.getDuty());
        this.setUscNo(dto.getUscNo());
        this.setAddr(dto.getAddr());
        this.setAddrNo(dto.getAddrNo());
        this.setStatus(dto.getStatus());
        this.setEnable(dto.getEnable());
        this.setAllowUserJoin(dto.getAllowUserJoin());
        this.setSort(dto.getSort());
        this.setTmp(dto.getTmp());
        //noinspection unchecked
        return (T) this;
    }

    public SysDept toDto() {
        SysDept dto = new SysDept();
        dto.setId(this.getId());
        dto.setParentId(this.getParentId());
        dto.setNodeKey(this.getNodeKey());
        dto.setNodePath(this.getNodePath());
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setAlias(this.getAlias());
        dto.setShortName(this.getShortName());
        dto.setType(this.getType());
        dto.setDuty(this.getDuty());
        dto.setUscNo(this.getUscNo());
        dto.setAddr(this.getAddr());
        dto.setAddrNo(this.getAddrNo());
        dto.setStatus(this.getStatus());
        dto.setEnable(this.getEnable());
        dto.setAllowUserJoin(this.getAllowUserJoin());
        dto.setSort(this.getSort());
        dto.setTmp(this.getTmp());
        return dto;
    }
}