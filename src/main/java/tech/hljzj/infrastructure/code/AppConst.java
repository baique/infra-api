package tech.hljzj.infrastructure.code;

public class AppConst {
    public static final String ID = "0";
    public static final String TREE_DELIMITER = "/";
    public static final String YES = "1";
    public static final String NOT = "0";
    public static final String CONFIG_DEFAULT_PASSWORD = "default_password";
    public static final String CONFIG_MAX_TRY_LOGIN_COUNT = "max_try_login_count";
    public static final String CONFIG_AUTO_UNLOCK = "auto_un_lock";
    public static final String CONFIG_ALLOW_SELF_DROP = "allow_self_drop";
    public static final String CONFIG_PASSWORD_STRENGTH = "password_strength";
    public static final String CONFIG_PASSWORD_SCORE = "password_score";
    public static final String CONFIG_PASSWORD_STRENGTH_DESC = "password_strength_desc";
    public static final String CONFIG_PASSWORD_EXPIRED = "password_expired";
    public static final String CONFIG_VALIDATE_PASSWORD_EQ = "password_not_eq";
    public static final String CONFIG_ENABLE_BIND_IP_CHECK = "enable_bind_ip_check";

    /**
     * 密码策略
     */
    public static class PASSWORD_POLICY {
        /**
         * 永不过期
         */
        public static final String NEVER = "1";
        /**
         * 下次登录时需要用户修改
         */
        public static final String NEXT_LOGIN_TIME_MUST_UPDATE = "2";
        /**
         * 指定时间过期
         */
        public static final String EXPIRED_AT = "3";
        /**
         * 禁止用户修改密码
         */
        public static final String DISABLE_CHANGE_PASSWORD = "4";
        /**
         * 已过期
         */
        public static final String EXPIRED = "5";
    }

    /**
     * 部门授权策略
     */
    public static class DeptGrantScope {
        /**
         * 仅本级
         */
        public static final int SELF = 1;
        /**
         * 下级
         */
        public static final int CHILDREN = 2;
        /**
         * 所有
         */
        public static final int DESCENDANT = 3;
    }
}
