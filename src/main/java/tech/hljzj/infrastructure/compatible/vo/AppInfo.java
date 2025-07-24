package tech.hljzj.infrastructure.compatible.vo;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysApp;

/**
 * 应用表
 */
@Setter
@Getter
public class AppInfo implements java.io.Serializable {
    private String appId;
    private String appTypeID;
    private String appDepartmentID;
    private String appName;
    private String appAddress;
    private String appSecret;
    private Integer appState;

    public static AppInfo fromDto(SysApp app) {
        AppInfo appInfo = new AppInfo();
        appInfo.setAppId(app.getId());
        appInfo.setAppName(app.getName());
        appInfo.setAppState(1);
        return appInfo;
    }
}