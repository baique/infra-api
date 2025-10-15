package tech.hljzj.infrastructure.vo.SysLog.base;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysLog;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志 sys_log_ 
 * 交互实体 用于更新
 *
 * @author wa
 */
@Getter
@Setter
public class SysLogUpdateBaseVo implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * id_
     */
    private String id;
    /**
     * 操作应用
     */
    private String appName;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 功能名称
     */
    private String functionName;
    /**
     * 操作类型
     */
    private String operType;
    /**
     * 操作描述
     */
    private String operContent;
    /**
     * 状态代码
     */
    private Integer errorCode;
    /**
     * 请求地址
     */
    private String addr;
    /**
     * 传入参数
     */
    private String params;
    /**
     * 返回数据
     */
    private String result;
    /**
     * 客户端IP
     */
    private String clientIp;
    /**
     * 客户端终端
     */
    private String clientAgent;
    /**
     * 来源应用
     */
    private String opAppId;
    /**
     * 操作人ID
     */
    private String opUserId;
    /**
     * 操作人账号
     */
    private String opUserUsername;
    /**
     * 操作人姓名
     */
    private String opUserRealname;
    /**
     * 操作人部门标识
     */
    private String opOrgId;
    /**
     * 操作人部门
     */
    private String opOrgName;
    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 转换为实际的数据实体
     */
    public SysLog toDto(){
        SysLog dto = new SysLog();
          
        dto.setId(this.getId());
        dto.setAppName(this.getAppName());
        dto.setModuleName(this.getModuleName());
        dto.setFunctionName(this.getFunctionName());
        dto.setOperType(this.getOperType());
        dto.setOperContent(this.getOperContent());
        dto.setErrorCode(this.getErrorCode());
        dto.setAddr(this.getAddr());
        dto.setParams(this.getParams());
        dto.setResult(this.getResult());
        dto.setClientIp(this.getClientIp());
        dto.setClientAgent(this.getClientAgent());
        dto.setOpAppId(this.getOpAppId());
        dto.setOpUserId(this.getOpUserId());
        dto.setOpUserUsername(this.getOpUserUsername());
        dto.setOpUserRealname(this.getOpUserRealname());
        dto.setOpOrgId(this.getOpOrgId());
        dto.setOpOrgName(this.getOpOrgName());
        dto.setOpTime(this.getOpTime());

        return dto;
    }

}