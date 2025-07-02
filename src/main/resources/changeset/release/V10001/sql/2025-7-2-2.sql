drop table if exists `sys_dept_identity_`;
CREATE TABLE IF NOT EXISTS `sys_dept_identity_`
(
    `id_`      char(32),
    `dept_id_` char(128) COMMENT '部门标识',
    `key_`     varchar(100) COMMENT '身份标识',
    `name_`    varchar(255) COMMENT '身份说明',
    `sort_`    int COMMENT '岗位顺序',
    `desc_`    varchar(2000) COMMENT '岗位描述',
    PRIMARY KEY (`id_`) USING BTREE,
    INDEX      `idx_dept_identity_dept_id`(`dept_id_` ASC) USING BTREE,
    INDEX      `idx_dept_identity_key`(`key_` ASC) USING BTREE
) 