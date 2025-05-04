package tech.hljzj.infrastructure.vo.SysLog.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.pojo.form.PageDomain;
import tech.hljzj.infrastructure.domain.SysLog;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * 操作日志 sys_log_
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysLogQueryBaseVo extends PageDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id_
     */
    private String id, idNot, idLike, idPrefix, idSuffix;
    private List<String> idIn, idNotIn;

    /**
     * 操作应用
     */
    private String appName, appNameNot, appNameLike, appNamePrefix, appNameSuffix;
    private List<String> appNameIn, appNameNotIn;

    /**
     * 模块名称
     */
    private String moduleName, moduleNameNot, moduleNameLike, moduleNamePrefix, moduleNameSuffix;
    private List<String> moduleNameIn, moduleNameNotIn;

    /**
     * 功能名称
     */
    private String functionName, functionNameNot, functionNameLike, functionNamePrefix, functionNameSuffix;
    private List<String> functionNameIn, functionNameNotIn;

    /**
     * 操作类型
     */
    private String operType, operTypeNot, operTypeLike, operTypePrefix, operTypeSuffix;
    private List<String> operTypeIn, operTypeNotIn;

    /**
     * 操作描述
     */
    private String operContent, operContentNot, operContentLike, operContentPrefix, operContentSuffix;
    private List<String> operContentIn, operContentNotIn;

    /**
     * 状态代码
     */
    private Integer errorCode, errorCodeNot, errorCodeGt, errorCodeGte, errorCodeLt, errorCodeLte;
    private List<Integer> errorCodeIn, errorCodeNotIn;

    /**
     * 请求地址
     */
    private String addr, addrNot, addrLike, addrPrefix, addrSuffix;
    private List<String> addrIn, addrNotIn;

    /**
     * 传入参数
     */
    private String params, paramsNot, paramsLike, paramsPrefix, paramsSuffix;
    private List<String> paramsIn, paramsNotIn;

    /**
     * 返回数据
     */
    private String result, resultNot, resultLike, resultPrefix, resultSuffix;
    private List<String> resultIn, resultNotIn;

    /**
     * 客户端IP
     */
    private String clientIp, clientIpNot, clientIpLike, clientIpPrefix, clientIpSuffix;
    private List<String> clientIpIn, clientIpNotIn;

    /**
     * 客户端终端
     */
    private String clientAgent, clientAgentNot, clientAgentLike, clientAgentPrefix, clientAgentSuffix;
    private List<String> clientAgentIn, clientAgentNotIn;

    /**
     * 来源应用
     */
    private String opAppId, opAppIdNot, opAppIdLike, opAppIdPrefix, opAppIdSuffix;
    private List<String> opAppIdIn, opAppIdNotIn;

    /**
     * 操作人ID
     */
    private String opUserId, opUserIdNot, opUserIdLike, opUserIdPrefix, opUserIdSuffix;
    private List<String> opUserIdIn, opUserIdNotIn;

    /**
     * 操作人账号
     */
    private String opUserUsername, opUserUsernameNot, opUserUsernameLike, opUserUsernamePrefix, opUserUsernameSuffix;
    private List<String> opUserUsernameIn, opUserUsernameNotIn;

    /**
     * 操作人姓名
     */
    private String opUserRealname, opUserRealnameNot, opUserRealnameLike, opUserRealnamePrefix, opUserRealnameSuffix;
    private List<String> opUserRealnameIn, opUserRealnameNotIn;

    /**
     * 操作人部门标识
     */
    private String opOrgId, opOrgIdNot, opOrgIdLike, opOrgIdPrefix, opOrgIdSuffix;
    private List<String> opOrgIdIn, opOrgIdNotIn;

    /**
     * 操作人部门
     */
    private String opOrgName, opOrgNameNot, opOrgNameLike, opOrgNamePrefix, opOrgNameSuffix;
    private List<String> opOrgNameIn, opOrgNameNotIn;

    /**
     * 操作时间
     */
    private Date opTime, opTimeNot, opTimeGt, opTimeGte, opTimeLt, opTimeLte;
    private List<Date> opTimeIn, opTimeNotIn;


    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionId() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getId()), SysLog::getId, StrUtil.trim(this.getId()));
            builder.ne(StrUtil.isNotBlank(this.getIdNot()), T::getId, StrUtil.trim(this.getIdNot()));
            builder.in(null != this.getIdIn() && this.getIdIn().size() > 0, T::getId, this.getIdIn());
            builder.notIn(null != this.getIdNotIn() && this.getIdNotIn().size() > 0, T::getId, this.getIdNotIn());
            if (StrUtil.isNotBlank(this.getIdLike())) {
                builder.like(T::getId, StrUtil.trim(this.getIdLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getIdPrefix()), T::getId, StrUtil.trim(this.getIdPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getIdSuffix()), T::getId, StrUtil.trim(this.getIdSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionAppName() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getAppName()), SysLog::getAppName, StrUtil.trim(this.getAppName()));
            builder.ne(StrUtil.isNotBlank(this.getAppNameNot()), T::getAppName, StrUtil.trim(this.getAppNameNot()));
            builder.in(null != this.getAppNameIn() && this.getAppNameIn().size() > 0, T::getAppName, this.getAppNameIn());
            builder.notIn(null != this.getAppNameNotIn() && this.getAppNameNotIn().size() > 0, T::getAppName, this.getAppNameNotIn());
            if (StrUtil.isNotBlank(this.getAppNameLike())) {
                builder.like(T::getAppName, StrUtil.trim(this.getAppNameLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getAppNamePrefix()), T::getAppName, StrUtil.trim(this.getAppNamePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getAppNameSuffix()), T::getAppName, StrUtil.trim(this.getAppNameSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionModuleName() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getModuleName()), SysLog::getModuleName, StrUtil.trim(this.getModuleName()));
            builder.ne(StrUtil.isNotBlank(this.getModuleNameNot()), T::getModuleName, StrUtil.trim(this.getModuleNameNot()));
            builder.in(null != this.getModuleNameIn() && this.getModuleNameIn().size() > 0, T::getModuleName, this.getModuleNameIn());
            builder.notIn(null != this.getModuleNameNotIn() && this.getModuleNameNotIn().size() > 0, T::getModuleName, this.getModuleNameNotIn());
            if (StrUtil.isNotBlank(this.getModuleNameLike())) {
                builder.like(T::getModuleName, StrUtil.trim(this.getModuleNameLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getModuleNamePrefix()), T::getModuleName, StrUtil.trim(this.getModuleNamePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getModuleNameSuffix()), T::getModuleName, StrUtil.trim(this.getModuleNameSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionFunctionName() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getFunctionName()), SysLog::getFunctionName, StrUtil.trim(this.getFunctionName()));
            builder.ne(StrUtil.isNotBlank(this.getFunctionNameNot()), T::getFunctionName, StrUtil.trim(this.getFunctionNameNot()));
            builder.in(null != this.getFunctionNameIn() && this.getFunctionNameIn().size() > 0, T::getFunctionName, this.getFunctionNameIn());
            builder.notIn(null != this.getFunctionNameNotIn() && this.getFunctionNameNotIn().size() > 0, T::getFunctionName, this.getFunctionNameNotIn());
            if (StrUtil.isNotBlank(this.getFunctionNameLike())) {
                builder.like(T::getFunctionName, StrUtil.trim(this.getFunctionNameLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getFunctionNamePrefix()), T::getFunctionName, StrUtil.trim(this.getFunctionNamePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getFunctionNameSuffix()), T::getFunctionName, StrUtil.trim(this.getFunctionNameSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionOperType() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getOperType()), SysLog::getOperType, StrUtil.trim(this.getOperType()));
            builder.ne(StrUtil.isNotBlank(this.getOperTypeNot()), T::getOperType, StrUtil.trim(this.getOperTypeNot()));
            builder.in(null != this.getOperTypeIn() && this.getOperTypeIn().size() > 0, T::getOperType, this.getOperTypeIn());
            builder.notIn(null != this.getOperTypeNotIn() && this.getOperTypeNotIn().size() > 0, T::getOperType, this.getOperTypeNotIn());
            if (StrUtil.isNotBlank(this.getOperTypeLike())) {
                builder.like(T::getOperType, StrUtil.trim(this.getOperTypeLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getOperTypePrefix()), T::getOperType, StrUtil.trim(this.getOperTypePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getOperTypeSuffix()), T::getOperType, StrUtil.trim(this.getOperTypeSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionOperContent() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getOperContent()), SysLog::getOperContent, StrUtil.trim(this.getOperContent()));
            builder.ne(StrUtil.isNotBlank(this.getOperContentNot()), T::getOperContent, StrUtil.trim(this.getOperContentNot()));
            builder.in(null != this.getOperContentIn() && this.getOperContentIn().size() > 0, T::getOperContent, this.getOperContentIn());
            builder.notIn(null != this.getOperContentNotIn() && this.getOperContentNotIn().size() > 0, T::getOperContent, this.getOperContentNotIn());
            if (StrUtil.isNotBlank(this.getOperContentLike())) {
                builder.like(T::getOperContent, StrUtil.trim(this.getOperContentLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getOperContentPrefix()), T::getOperContent, StrUtil.trim(this.getOperContentPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getOperContentSuffix()), T::getOperContent, StrUtil.trim(this.getOperContentSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionErrorCode() {
        return (builder) -> {

            builder.eq(null != this.getErrorCode(), SysLog::getErrorCode, (this.getErrorCode()));
            builder.ne(null != this.getErrorCodeNot(), T::getErrorCode, (this.getErrorCodeNot()));
            builder.in(null != this.getErrorCodeIn() && this.getErrorCodeIn().size() > 0, T::getErrorCode, this.getErrorCodeIn());
            builder.notIn(null != this.getErrorCodeNotIn() && this.getErrorCodeNotIn().size() > 0, T::getErrorCode, this.getErrorCodeNotIn());
            builder.gt(this.getErrorCodeGt() != null, T::getErrorCode, this.getErrorCodeGt());
            builder.ge(this.getErrorCodeGte() != null, T::getErrorCode, this.getErrorCodeGte());
            builder.lt(this.getErrorCodeLt() != null, T::getErrorCode, this.getErrorCodeLt());
            builder.le(this.getErrorCodeLte() != null, T::getErrorCode, this.getErrorCodeLte());


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionAddr() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getAddr()), SysLog::getAddr, StrUtil.trim(this.getAddr()));
            builder.ne(StrUtil.isNotBlank(this.getAddrNot()), T::getAddr, StrUtil.trim(this.getAddrNot()));
            builder.in(null != this.getAddrIn() && this.getAddrIn().size() > 0, T::getAddr, this.getAddrIn());
            builder.notIn(null != this.getAddrNotIn() && this.getAddrNotIn().size() > 0, T::getAddr, this.getAddrNotIn());
            if (StrUtil.isNotBlank(this.getAddrLike())) {
                builder.like(T::getAddr, StrUtil.trim(this.getAddrLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getAddrPrefix()), T::getAddr, StrUtil.trim(this.getAddrPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getAddrSuffix()), T::getAddr, StrUtil.trim(this.getAddrSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionParams() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getParams()), SysLog::getParams, StrUtil.trim(this.getParams()));
            builder.ne(StrUtil.isNotBlank(this.getParamsNot()), T::getParams, StrUtil.trim(this.getParamsNot()));
            builder.in(null != this.getParamsIn() && this.getParamsIn().size() > 0, T::getParams, this.getParamsIn());
            builder.notIn(null != this.getParamsNotIn() && this.getParamsNotIn().size() > 0, T::getParams, this.getParamsNotIn());
            if (StrUtil.isNotBlank(this.getParamsLike())) {
                builder.like(T::getParams, StrUtil.trim(this.getParamsLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getParamsPrefix()), T::getParams, StrUtil.trim(this.getParamsPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getParamsSuffix()), T::getParams, StrUtil.trim(this.getParamsSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionResult() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getResult()), SysLog::getResult, StrUtil.trim(this.getResult()));
            builder.ne(StrUtil.isNotBlank(this.getResultNot()), T::getResult, StrUtil.trim(this.getResultNot()));
            builder.in(null != this.getResultIn() && this.getResultIn().size() > 0, T::getResult, this.getResultIn());
            builder.notIn(null != this.getResultNotIn() && this.getResultNotIn().size() > 0, T::getResult, this.getResultNotIn());
            if (StrUtil.isNotBlank(this.getResultLike())) {
                builder.like(T::getResult, StrUtil.trim(this.getResultLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getResultPrefix()), T::getResult, StrUtil.trim(this.getResultPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getResultSuffix()), T::getResult, StrUtil.trim(this.getResultSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionClientIp() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getClientIp()), SysLog::getClientIp, StrUtil.trim(this.getClientIp()));
            builder.ne(StrUtil.isNotBlank(this.getClientIpNot()), T::getClientIp, StrUtil.trim(this.getClientIpNot()));
            builder.in(null != this.getClientIpIn() && this.getClientIpIn().size() > 0, T::getClientIp, this.getClientIpIn());
            builder.notIn(null != this.getClientIpNotIn() && this.getClientIpNotIn().size() > 0, T::getClientIp, this.getClientIpNotIn());
            if (StrUtil.isNotBlank(this.getClientIpLike())) {
                builder.like(T::getClientIp, StrUtil.trim(this.getClientIpLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getClientIpPrefix()), T::getClientIp, StrUtil.trim(this.getClientIpPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getClientIpSuffix()), T::getClientIp, StrUtil.trim(this.getClientIpSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionClientAgent() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getClientAgent()), SysLog::getClientAgent, StrUtil.trim(this.getClientAgent()));
            builder.ne(StrUtil.isNotBlank(this.getClientAgentNot()), T::getClientAgent, StrUtil.trim(this.getClientAgentNot()));
            builder.in(null != this.getClientAgentIn() && this.getClientAgentIn().size() > 0, T::getClientAgent, this.getClientAgentIn());
            builder.notIn(null != this.getClientAgentNotIn() && this.getClientAgentNotIn().size() > 0, T::getClientAgent, this.getClientAgentNotIn());
            if (StrUtil.isNotBlank(this.getClientAgentLike())) {
                builder.like(T::getClientAgent, StrUtil.trim(this.getClientAgentLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getClientAgentPrefix()), T::getClientAgent, StrUtil.trim(this.getClientAgentPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getClientAgentSuffix()), T::getClientAgent, StrUtil.trim(this.getClientAgentSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionOpAppId() {
        return (builder) -> {
            builder.eq(StrUtil.isNotBlank(this.getOpAppId()), SysLog::getOpAppId, StrUtil.trim(this.getOpAppId()));
            builder.ne(StrUtil.isNotBlank(this.getOpAppIdNot()), T::getOpAppId, StrUtil.trim(this.getOpAppIdNot()));
            builder.in(null != this.getOpAppIdIn() && this.getOpAppIdIn().size() > 0, T::getOpAppId, this.getOpAppIdIn());
            builder.notIn(null != this.getOpAppIdNotIn() && this.getOpAppIdNotIn().size() > 0, T::getOpAppId, this.getOpAppIdNotIn());
            if (StrUtil.isNotBlank(this.getOpAppIdLike())) {
                builder.like(T::getOpAppId, StrUtil.trim(this.getOpAppIdLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getOpAppIdPrefix()), T::getOpAppId, StrUtil.trim(this.getOpAppIdPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getOpAppIdSuffix()), T::getOpAppId, StrUtil.trim(this.getOpAppIdSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionOpUserId() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getOpUserId()), SysLog::getOpUserId, StrUtil.trim(this.getOpUserId()));
            builder.ne(StrUtil.isNotBlank(this.getOpUserIdNot()), T::getOpUserId, StrUtil.trim(this.getOpUserIdNot()));
            builder.in(null != this.getOpUserIdIn() && this.getOpUserIdIn().size() > 0, T::getOpUserId, this.getOpUserIdIn());
            builder.notIn(null != this.getOpUserIdNotIn() && this.getOpUserIdNotIn().size() > 0, T::getOpUserId, this.getOpUserIdNotIn());
            if (StrUtil.isNotBlank(this.getOpUserIdLike())) {
                builder.like(T::getOpUserId, StrUtil.trim(this.getOpUserIdLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getOpUserIdPrefix()), T::getOpUserId, StrUtil.trim(this.getOpUserIdPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getOpUserIdSuffix()), T::getOpUserId, StrUtil.trim(this.getOpUserIdSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionOpUserUsername() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getOpUserUsername()), SysLog::getOpUserUsername, StrUtil.trim(this.getOpUserUsername()));
            builder.ne(StrUtil.isNotBlank(this.getOpUserUsernameNot()), T::getOpUserUsername, StrUtil.trim(this.getOpUserUsernameNot()));
            builder.in(null != this.getOpUserUsernameIn() && this.getOpUserUsernameIn().size() > 0, T::getOpUserUsername, this.getOpUserUsernameIn());
            builder.notIn(null != this.getOpUserUsernameNotIn() && this.getOpUserUsernameNotIn().size() > 0, T::getOpUserUsername, this.getOpUserUsernameNotIn());
            if (StrUtil.isNotBlank(this.getOpUserUsernameLike())) {
                builder.like(T::getOpUserUsername, StrUtil.trim(this.getOpUserUsernameLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getOpUserUsernamePrefix()), T::getOpUserUsername, StrUtil.trim(this.getOpUserUsernamePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getOpUserUsernameSuffix()), T::getOpUserUsername, StrUtil.trim(this.getOpUserUsernameSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionOpUserRealname() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getOpUserRealname()), SysLog::getOpUserRealname, StrUtil.trim(this.getOpUserRealname()));
            builder.ne(StrUtil.isNotBlank(this.getOpUserRealnameNot()), T::getOpUserRealname, StrUtil.trim(this.getOpUserRealnameNot()));
            builder.in(null != this.getOpUserRealnameIn() && this.getOpUserRealnameIn().size() > 0, T::getOpUserRealname, this.getOpUserRealnameIn());
            builder.notIn(null != this.getOpUserRealnameNotIn() && this.getOpUserRealnameNotIn().size() > 0, T::getOpUserRealname, this.getOpUserRealnameNotIn());
            if (StrUtil.isNotBlank(this.getOpUserRealnameLike())) {
                builder.like(T::getOpUserRealname, StrUtil.trim(this.getOpUserRealnameLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getOpUserRealnamePrefix()), T::getOpUserRealname, StrUtil.trim(this.getOpUserRealnamePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getOpUserRealnameSuffix()), T::getOpUserRealname, StrUtil.trim(this.getOpUserRealnameSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionOpOrgId() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getOpOrgId()), SysLog::getOpOrgId, StrUtil.trim(this.getOpOrgId()));
            builder.ne(StrUtil.isNotBlank(this.getOpOrgIdNot()), T::getOpOrgId, StrUtil.trim(this.getOpOrgIdNot()));
            builder.in(null != this.getOpOrgIdIn() && this.getOpOrgIdIn().size() > 0, T::getOpOrgId, this.getOpOrgIdIn());
            builder.notIn(null != this.getOpOrgIdNotIn() && this.getOpOrgIdNotIn().size() > 0, T::getOpOrgId, this.getOpOrgIdNotIn());
            if (StrUtil.isNotBlank(this.getOpOrgIdLike())) {
                builder.like(T::getOpOrgId, StrUtil.trim(this.getOpOrgIdLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getOpOrgIdPrefix()), T::getOpOrgId, StrUtil.trim(this.getOpOrgIdPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getOpOrgIdSuffix()), T::getOpOrgId, StrUtil.trim(this.getOpOrgIdSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionOpOrgName() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getOpOrgName()), SysLog::getOpOrgName, StrUtil.trim(this.getOpOrgName()));
            builder.ne(StrUtil.isNotBlank(this.getOpOrgNameNot()), T::getOpOrgName, StrUtil.trim(this.getOpOrgNameNot()));
            builder.in(null != this.getOpOrgNameIn() && this.getOpOrgNameIn().size() > 0, T::getOpOrgName, this.getOpOrgNameIn());
            builder.notIn(null != this.getOpOrgNameNotIn() && this.getOpOrgNameNotIn().size() > 0, T::getOpOrgName, this.getOpOrgNameNotIn());
            if (StrUtil.isNotBlank(this.getOpOrgNameLike())) {
                builder.like(T::getOpOrgName, StrUtil.trim(this.getOpOrgNameLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getOpOrgNamePrefix()), T::getOpOrgName, StrUtil.trim(this.getOpOrgNamePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getOpOrgNameSuffix()), T::getOpOrgName, StrUtil.trim(this.getOpOrgNameSuffix()));
            }


        };
    }

    public <T extends SysLog> Consumer<LambdaQueryWrapper<T>> conditionOpTime() {
        return (builder) -> {

            builder.eq(null != this.getOpTime(), SysLog::getOpTime, (this.getOpTime()));
            builder.ne(null != this.getOpTimeNot(), T::getOpTime, (this.getOpTimeNot()));
            builder.in(null != this.getOpTimeIn() && this.getOpTimeIn().size() > 0, T::getOpTime, this.getOpTimeIn());
            builder.notIn(null != this.getOpTimeNotIn() && this.getOpTimeNotIn().size() > 0, T::getOpTime, this.getOpTimeNotIn());
            builder.gt(this.getOpTimeGt() != null, T::getOpTime, this.getOpTimeGt());
            builder.ge(this.getOpTimeGte() != null, T::getOpTime, this.getOpTimeGte());
            builder.lt(this.getOpTimeLt() != null, T::getOpTime, this.getOpTimeLt());
            builder.le(this.getOpTimeLte() != null, T::getOpTime, this.getOpTimeLte());


        };
    }

    /**
     * 构建查询条件
     */
    public <T extends SysLog> LambdaQueryWrapper<T> buildQueryWrapper() {
        LambdaQueryWrapper<T> builder = Wrappers.<T>lambdaQuery();
        this.<T>conditionId().accept(builder);
        this.<T>conditionAppName().accept(builder);
        this.<T>conditionModuleName().accept(builder);
        this.<T>conditionFunctionName().accept(builder);
        this.<T>conditionOperType().accept(builder);
        this.<T>conditionOperContent().accept(builder);
        this.<T>conditionErrorCode().accept(builder);
        this.<T>conditionAddr().accept(builder);
        this.<T>conditionParams().accept(builder);
        this.<T>conditionResult().accept(builder);
        this.<T>conditionClientIp().accept(builder);
        this.<T>conditionClientAgent().accept(builder);
        this.<T>conditionOpAppId().accept(builder);
        this.<T>conditionOpUserId().accept(builder);
        this.<T>conditionOpUserUsername().accept(builder);
        this.<T>conditionOpUserRealname().accept(builder);
        this.<T>conditionOpOrgId().accept(builder);
        this.<T>conditionOpOrgName().accept(builder);
        this.<T>conditionOpTime().accept(builder);
        return builder;
    }

}