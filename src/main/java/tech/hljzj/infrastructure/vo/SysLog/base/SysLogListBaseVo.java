package tech.hljzj.infrastructure.vo.SysLog.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import tech.hljzj.infrastructure.domain.SysLog;
import java.lang.*;
import java.util.Date;


/**
 * 操作日志 sys_log_ 
 * 交互实体 用于列表检索和导出
 *
 * @author wa
 */
@Getter
@Setter
public class SysLogListBaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * id_
     */
    @ExcelProperty(value = "id_")
    private String id;
    /**
     * 操作应用
     */
    @ExcelProperty(value = "操作应用")
    private String appName;
    /**
     * 模块名称
     */
    @ExcelProperty(value = "模块名称")
    private String moduleName;
    /**
     * 功能名称
     */
    @ExcelProperty(value = "功能名称")
    private String functionName;
    /**
     * 操作类型
     */
    @ExcelProperty(value = "操作类型")
    private String operType;
    /**
     * 操作描述
     */
    @ExcelProperty(value = "操作描述")
    private String operContent;
    /**
     * 状态代码
     */
    @ExcelProperty(value = "状态代码")
    private Integer errorCode;
    /**
     * 请求地址
     */
    @ExcelProperty(value = "请求地址")
    private String addr;
    /**
     * 传入参数
     */
    @ExcelProperty(value = "传入参数")
    private String params;
    /**
     * 返回数据
     */
    @ExcelProperty(value = "返回数据")
    private String result;
    /**
     * 客户端IP
     */
    @ExcelProperty(value = "客户端IP")
    private String clientIp;
    /**
     * 客户端终端
     */
    @ExcelProperty(value = "客户端终端")
    private String clientAgent;
    /**
     * 来源应用
     */
    @ExcelProperty(value = "来源应用")
    private String opAppId;
    /**
     * 操作人ID
     */
    @ExcelProperty(value = "操作人ID")
    private String opUserId;
    /**
     * 操作人账号
     */
    @ExcelProperty(value = "操作人账号")
    private String opUserUsername;
    /**
     * 操作人姓名
     */
    @ExcelProperty(value = "操作人姓名")
    private String opUserRealname;
    /**
     * 操作人部门标识
     */
    @ExcelProperty(value = "操作人部门标识")
    private String opOrgId;
    /**
     * 操作人部门
     */
    @ExcelProperty(value = "操作人部门")
    private String opOrgName;
    /**
     * 操作时间
     */
    @ExcelProperty(value = "操作时间")
    private Date opTime;

    public <T extends SysLogListBaseVo> T fromDto(SysLog dto){
        this.setId(dto.getId());
        this.setAppName(dto.getAppName());
        this.setModuleName(dto.getModuleName());
        this.setFunctionName(dto.getFunctionName());
        this.setOperType(dto.getOperType());
        this.setOperContent(dto.getOperContent());
        this.setErrorCode(dto.getErrorCode());
        this.setAddr(dto.getAddr());
        this.setParams(dto.getParams());
        this.setResult(dto.getResult());
        this.setClientIp(dto.getClientIp());
        this.setClientAgent(dto.getClientAgent());
        this.setOpAppId(dto.getOpAppId());
        this.setOpUserId(dto.getOpUserId());
        this.setOpUserUsername(dto.getOpUserUsername());
        this.setOpUserRealname(dto.getOpUserRealname());
        this.setOpOrgId(dto.getOpOrgId());
        this.setOpOrgName(dto.getOpOrgName());
        this.setOpTime(dto.getOpTime());
        //noinspection unchecked
        return (T) this;
    }

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