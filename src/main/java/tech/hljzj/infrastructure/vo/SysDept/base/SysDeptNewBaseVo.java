package tech.hljzj.infrastructure.vo.SysDept.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.lang.*;
import java.util.Date;
import tech.hljzj.infrastructure.domain.SysDept;

/**
 * 组织机构 sys_dept_ 
 * 交互实体 用于新增
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptNewBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 上级组织
     */
    private String parentId;
    /**
     * 组织标识
     */
    private String key;
    /**
     * 组织名称
     */
    private String name;
    /**
     * 组织别名
     */
    private String alias;
    /**
     * 组织简称
     */
    private String shortName;
    /**
     * 组织类型
     */
    private String type;
    /**
     * 组织责任说明
     */
    private String duty;
    /**
     * 统一社会信用代码
     */
    private String uscNo;
    /**
     * 所在地点
     */
    private String addr;
    /**
     * 所在地点编码
     */
    private String addrNo;
    /**
     * 状态
     */
    private String status;
    /**
     * 是否启用
     */
    private String enable;
    /**
     * 允许用户加入
     */
    private String allowUserJoin;
    /**
     * 排序编号
     */
    private Integer sort;
    /**
     * 是否临时组织
     */
    private String tmp;

    /**
     * 转换为实际的数据实体
     */
    public SysDept toDto(){
        SysDept dto = new SysDept();
          
        dto.setParentId(this.getParentId());
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