package tech.hljzj.infrastructure.util;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.util.web.MsgUtil;
import tech.hljzj.framework.util.web.ReqUtil;
import tech.hljzj.infrastructure.code.AppConst;

import javax.servlet.http.HttpServletRequest;

/**
 * 应用上下文信息
 */
public class AppScopeHolder {
    public static String AK_HEADER_NAME = "x-ak-id";

    /**
     * 获取当前请求所限定的应用范围
     *
     * @return 应用范围
     */
    public static String getScopeAppId() {
        HttpServletRequest req = ReqUtil.getReq();
        if (req == null) {
            return null;
        }
        Object ownerAppId = req.getAttribute(AK_HEADER_NAME);
        if (StrUtil.isBlankIfStr(ownerAppId)) {
            String appToken = req.getHeader(AK_HEADER_NAME);
            if (StrUtil.isNotBlank(appToken)) {
                String appId;
                try {
                    DecodedJWT decode = JWT.decode(appToken);
                    appId = decode.getKeyId();
                } catch (Exception e) {
                    throw UserException.defaultError("应用标识解析异常");
                }
                req.setAttribute(AK_HEADER_NAME, appId);
                return appId;
            } else {
                return null;
            }
            //取出应用标识

        }
        return ownerAppId.toString();
    }


    public static String getRawAppToken(){
        HttpServletRequest req = ReqUtil.getReq();
        if (req == null) {
            return null;
        }
        return req.getHeader(AK_HEADER_NAME);
    }

    /**
     * 设置应用标识
     *
     * @param appId 应用标识
     */
    public static void setScopeAppId(String appId) {
        HttpServletRequest req = ReqUtil.getReq();
        if (req != null) {
            req.setAttribute(AK_HEADER_NAME, appId);
        }
    }

    /**
     * 获取当前请求所限定的应用范围
     *
     * @return 应用范围
     * @throws UserException 当应用不存在时将报错
     */
    public static String requiredScopeAppId() throws UserException {
        String scopeAppId = getScopeAppId();
        if (StrUtil.isBlank(scopeAppId)) {
            throw UserException.defaultError(MsgUtil.t("auth.unknownSourceClient"));
        }
        return scopeAppId;
    }

    /**
     * 获取访问应用，如果没有设置访问应用，那么就使用当前应用
     *
     * @return 获取
     */
    public static String getScopeAppIdOrDefault() {
        return StrUtil.blankToDefault(getScopeAppId(), AppConst.ID);
    }

}
