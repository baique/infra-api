package tech.hljzj.infrastructure.vo.SysApp.base;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.lang.*;
import java.util.Date;
import tech.hljzj.infrastructure.domain.SysApp;

/**
 * 应用管理 sys_app 
 * 交互实体 用于新增
 *
 * @author wa
 */
@Getter
@Setter
public class SysAppNewBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 应用标识
     */
    private String key;
    /**
     * 应用名称
     */
    private String name;
    /**
     * 主页地址
     */
    private String mainPagePath;
    /**
     * 应用密钥
     */
    private String secret;
    /**
     * 应用状态
     */
    private String status;
    /**
     * 应用描述
     */
    private String desc;
    /**
     * 排序编号
     */
    private Integer sort;

    /**
     * 转换为实际的数据实体
     */
    public SysApp toDto(){
        SysApp dto = new SysApp();
          
        dto.setKey(this.getKey());
        dto.setName(this.getName());
        dto.setMainPagePath(this.getMainPagePath());
        dto.setSecret(this.getSecret());
        dto.setStatus(this.getStatus());
        dto.setDesc(this.getDesc());
        dto.setSort(this.getSort());

        return dto;
    }
}