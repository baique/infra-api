INSERT INTO  `sys_menu_` (`id_`, `owner_app_id_`, `node_key_`, `node_path_`, `key_`, `name_`, `status_`, `desc_`, `default_grant_`, `visible_`, `menu_type_`, `path_`, `component_`, `icon_`, `tag_`, `classes_`, `sort_`, `parent_id_`, `create_by_`, `create_time_`, `update_by_`, `update_time_`, `owner_user_id_`, `owner_user_account_`, `owner_dept_id_`, `owner_dept_code_`, `owner_dept_name_`) VALUES ('13d0053b3a047854631aad709377a5a7', '0', 'bb7uWjM6', '/a0fef405487367a4/bb7uWjM6', 'sys:dept:grant', '授权角色', '1', NULL, '0', '1', 'B', NULL, NULL, NULL, NULL, NULL, 250, '766d2e26ab9d13412534137f40459ca3', '系统运维', '2025-07-01 14:08:35', '系统运维', '2025-07-01 14:08:57', '08c13e778cbd40826f394a515cb0f6c0', 'devops', 'ed7386f15e8860834583cd0552a3630c', 'system_devops_user', '系统运维管理');
alter table sys_user_role_ add column expired_time_ datetime;
COMMENT ON COLUMN sys_user_role_.expired_time_ IS '角色过期时间';

CREATE TABLE IF NOT EXISTS `sys_dept_role_`  (
    `id_` char(32),
    `dept_id_` char(128),
    `role_id_` char(32),
    `app_id_` char(32),
    `expired_time_` datetime,
    scope_ int(1),
    PRIMARY KEY (`id_`) USING BTREE
);

CREATE INDEX idx_dept_role_dept_id ON sys_dept_role_ (dept_id_);
CREATE INDEX idx_dept_role_role_id ON sys_dept_role_ (role_id_);
CREATE INDEX idx_dept_role_app_id ON sys_dept_role_ (app_id_);

DROP TABLE IF EXISTS sys_dept_identity_;
CREATE TABLE sys_dept_identity_ (
    id_       CHAR(32) PRIMARY KEY,
    dept_id_  CHAR(128),
    key_      VARCHAR(100),
    name_     VARCHAR(255),
    sort_     INTEGER,
    desc_     VARCHAR(2000)
);

COMMENT ON COLUMN sys_dept_identity_.dept_id_ IS '部门标识';
COMMENT ON COLUMN sys_dept_identity_.key_ IS '身份标识';
COMMENT ON COLUMN sys_dept_identity_.name_ IS '身份说明';
COMMENT ON COLUMN sys_dept_identity_.sort_ IS '岗位顺序';
COMMENT ON COLUMN sys_dept_identity_.desc_ IS '岗位描述';

CREATE INDEX idx_dept_identity_dept_id ON sys_dept_identity_ (dept_id_);
CREATE INDEX idx_dept_identity_key ON sys_dept_identity_ (key_);
