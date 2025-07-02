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
