CREATE TABLE IF NOT EXISTS `sys_app_`  (
  `id_` char(32)  NOT NULL,
  `key_` varchar(100)  COMMENT '应用标识',
  `name_` varchar(125)  COMMENT '应用名称',
  `main_page_path_` varchar(255)  COMMENT '主页地址',
  `secret_` varchar(50)  COMMENT '应用密钥',
  `status_` char(1)  COMMENT '应用状态',
  `desc_` varchar(2000)  COMMENT '应用描述',
  `sort_` int COMMENT '排序编号',
  `create_by_` varchar(128)  COMMENT '创建人姓名',
  `create_time_` datetime COMMENT '创建时间',
  `update_by_` varchar(128)  COMMENT '修改人姓名',
  `update_time_` datetime COMMENT '修改时间',
  `owner_user_id_` varchar(128)  COMMENT '所属用户标识',
  `owner_user_account_` varchar(255)  COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128)  COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255)  COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255)  COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_app_key`(`key_` ASC) USING BTREE,
  INDEX `idx_app_status`(`status_` ASC) USING BTREE
)  COMMENT = '应用管理' ;

-- ----------------------------
-- Table structure for sys_config_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_config_`  (
  `id_` char(32)  NOT NULL,
  `owner_app_id_` char(64)  COMMENT '所属应用标识',
  `key_` varchar(100)  COMMENT '配置标识',
  `name_` varchar(255)  COMMENT '配置名称',
  `value_` varchar(255)  COMMENT '配置项值',
  `status_` char(1)  COMMENT '配置状态',
  `desc_` varchar(2000)  COMMENT '配置描述',
  `sort_` int COMMENT '排序编号',
  `locked_` char(1)  COMMENT '是否锁定',
  `create_by_` varchar(128)  COMMENT '创建人姓名',
  `create_time_` datetime COMMENT '创建时间',
  `update_by_` varchar(128)  COMMENT '修改人姓名',
  `update_time_` datetime COMMENT '修改时间',
  `owner_user_id_` varchar(128)  COMMENT '所属用户标识',
  `owner_user_account_` varchar(255)  COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128)  COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255)  COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255)  COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_config_app_id`(`owner_app_id_` ASC) USING BTREE,
  INDEX `idx_config_key`(`key_` ASC) USING BTREE,
  INDEX `idx_config_status`(`status_` ASC) USING BTREE
)  COMMENT = '系统配置' ;

-- ----------------------------
-- Table structure for sys_dept_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_dept_`  (
  `id_` char(128)  NOT NULL,
  `parent_id_` char(128)  COMMENT '上级组织',
  `node_key_` char(16)  COMMENT '本级节点标识',
  `node_path_` varchar(2000)  COMMENT '祖先节点路径',
  `key_` varchar(100)  COMMENT '组织标识',
  `name_` varchar(255)  COMMENT '组织名称',
  `alias_` varchar(100)  COMMENT '组织别名',
  `short_name_` varchar(100)  COMMENT '组织简称',
  `management_relation_` varchar(20)  COMMENT '管理关系',
  `type_` varchar(20)  COMMENT '组织类型',
  `duty_` varchar(2000)  COMMENT '组织责任说明',
  `usc_no_` varchar(100)  COMMENT '统一社会信用代码',
  `addr_` varchar(2000)  COMMENT '所在地点',
  `addr_no_` varchar(255)  COMMENT '所在地点编码',
  `status_` char(1)  COMMENT '状态',
  `enable_` char(1)  COMMENT '是否启用',
  `allow_user_join_` char(1)  COMMENT '是否允许用户加入',
  `sort_` int COMMENT '排序编号',
  `tmp_` char(1)  COMMENT '是否临时组织',
  `create_by_` varchar(128)  COMMENT '创建人姓名',
  `create_time_` datetime COMMENT '创建时间',
  `update_by_` varchar(128)  COMMENT '修改人姓名',
  `update_time_` datetime COMMENT '修改时间',
  `owner_user_id_` char(128)  COMMENT '所属用户标识',
  `owner_user_account_` varchar(255)  COMMENT '所属用户账号',
  `owner_dept_id_` char(128)  COMMENT '所属部门标识',
  `owner_dept_code_` varchar(100)  COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255)  COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_dept_parent_id`(`parent_id_` ASC) USING BTREE,
  INDEX `idx_dept_node_key`(`node_key_` ASC) USING BTREE,
  INDEX `idx_dept_key`(`key_` ASC) USING BTREE,
  INDEX `idx_dept_status`(`status_` ASC) USING BTREE,
  INDEX `idx_dept_enable`(`enable_` ASC) USING BTREE
)  COMMENT = '组织机构' ;

-- ----------------------------
-- Table structure for sys_dept_external_user_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_dept_external_user_`  (
  `id_` char(32)  NOT NULL,
  `dept_id_` char(128)  COMMENT '部门标识',
  `user_id_` char(128)  COMMENT '用户标识',
  `identity_` varchar(100)  COMMENT '用户身份',
  `remarks_` varchar(2000)  COMMENT '备注信息',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_dept_external_dept_id`(`dept_id_` ASC) USING BTREE,
  INDEX `idx_dept_external_user_id`(`user_id_` ASC) USING BTREE
)  COMMENT = '外部编入人员' ;

-- ----------------------------
-- Table structure for sys_dept_identity_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_dept_identity_`  (
  `id_` char(32)  NOT NULL,
  `dept_id_` char(128)  COMMENT '部门标识',
  `key_` varchar(100)  COMMENT '身份标识',
  `name_` varchar(255)  COMMENT '身份说明',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_dept_identity_dept_id`(`dept_id_` ASC) USING BTREE,
  INDEX `idx_dept_identity_key`(`key_` ASC) USING BTREE
)  COMMENT = '部门内岗位身份设置' ;

-- ----------------------------
-- Table structure for sys_dict_data_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_dict_data_`  (
  `id_` char(64)  NOT NULL,
  `owner_app_id_` char(128)  COMMENT '所属应用标识',
  `owner_type_id_` char(64)  COMMENT '所属字典类型标识',
  `key_` varchar(100)  COMMENT '字典标识',
  `name_` varchar(255)  COMMENT '字典名称',
  `value_` varchar(500)  COMMENT '字典项值',
  `list_class_` varchar(50)  COMMENT '数据样式',
  `selectable_` char(1)  COMMENT '是否可选',
  `status_` char(1)  COMMENT '字典状态',
  `help_message_` varchar(500)  COMMENT '选项说明',
  `desc_` varchar(2000)  COMMENT '字典描述',
  `locked_` char(1)  COMMENT '是否锁定',
  `sort_` int COMMENT '排序编号',
  `create_by_` varchar(128)  COMMENT '创建人姓名',
  `create_time_` datetime COMMENT '创建时间',
  `update_by_` varchar(128)  COMMENT '修改人姓名',
  `update_time_` datetime COMMENT '修改时间',
  `owner_user_id_` varchar(128)  COMMENT '所属用户标识',
  `owner_user_account_` varchar(255)  COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128)  COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255)  COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255)  COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_dict_data_app_id`(`owner_app_id_` ASC) USING BTREE,
  INDEX `idx_dict_data_type_id`(`owner_type_id_` ASC) USING BTREE,
  INDEX `idx_dict_data_key`(`key_` ASC) USING BTREE,
  INDEX `idx_dict_data_status`(`status_` ASC) USING BTREE
)  COMMENT = '字典项' ;

-- ----------------------------
-- Table structure for sys_dict_type_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_dict_type_`  (
  `id_` char(32)  NOT NULL,
  `owner_app_id_` char(128)  COMMENT '所属应用标识',
  `key_` varchar(100)  COMMENT '字典类型标识',
  `name_` varchar(255)  COMMENT '字典类型名称',
  `status_` char(1)  COMMENT '字典类型状态',
  `max_length_` int COMMENT '字典项值长度限制',
  `help_message_` varchar(255)  COMMENT '字典说明',
  `desc_` varchar(2000)  COMMENT '字典类型描述',
  `sort_` int COMMENT '排序编号',
  `create_by_` varchar(128)  COMMENT '创建人姓名',
  `create_time_` datetime COMMENT '创建时间',
  `update_by_` varchar(128)  COMMENT '修改人姓名',
  `update_time_` datetime COMMENT '修改时间',
  `owner_user_id_` varchar(128)  COMMENT '所属用户标识',
  `owner_user_account_` varchar(255)  COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128)  COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255)  COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255)  COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_dict_type_app_id`(`owner_app_id_` ASC) USING BTREE,
  INDEX `idx_dict_type_key`(`key_` ASC) USING BTREE,
  INDEX `idx_dict_type_status`(`status_` ASC) USING BTREE
)  COMMENT = '字典组' ;

-- ----------------------------
-- Table structure for sys_log_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_log_`  (
  `id_` char(64)  NOT NULL,
  `app_name_` varchar(255)  COMMENT '操作应用',
  `module_name_` varchar(500)  COMMENT '模块名称',
  `function_name_` varchar(500)  COMMENT '功能名称',
  `oper_type_` varchar(255)  COMMENT '操作类型',
  `oper_content_` varchar(2000)  COMMENT '操作描述',
  `error_code_` int COMMENT '错误状态代码',
  `addr_` varchar(500)  COMMENT '请求地址',
  `params_` longtext  NULL COMMENT '传入参数',
  `result_` longtext  NULL COMMENT '返回数据',
  `client_ip_` varchar(255)  COMMENT '客户端IP',
  `client_agent_` varchar(255)  COMMENT '客户端终端',
  `op_app_id_` char(128)  COMMENT '来源应用',
  `op_user_id_` char(128)  COMMENT '操作人ID',
  `op_user_username_` varchar(255)  COMMENT '操作人账号',
  `op_user_realname_` varchar(255)  COMMENT '操作人真实姓名',
  `op_org_id_` char(128)  COMMENT '操作人所在部门',
  `op_org_name_` varchar(255)  COMMENT '操作人所在部门名称',
  `op_time_` datetime COMMENT '操作时间',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_log_app_name`(`app_name_` ASC) USING BTREE,
  INDEX `idx_log_op_user_id`(`op_user_id_` ASC) USING BTREE,
  INDEX `idx_log_op_time`(`op_time_` ASC) USING BTREE,
  INDEX `idx_log_oper_type`(`oper_type_` ASC) USING BTREE
)  COMMENT = '操作日志' ;

-- ----------------------------
-- Table structure for sys_menu_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_menu_`  (
  `id_` char(32)  NOT NULL,
  `owner_app_id_` char(32)  COMMENT '所属应用标识',
  `node_key_` char(16)  COMMENT '节点标识',
  `node_path_` varchar(2000)  COMMENT '节点路径',
  `key_` varchar(100)  COMMENT '菜单标识，权限标识',
  `name_` varchar(255)  COMMENT '菜单名称',
  `status_` char(1)  COMMENT '菜单状态',
  `desc_` varchar(2000)  COMMENT '菜单描述',
  `default_grant_` char(1)  COMMENT '默认授予登录用户',
  `visible_` char(1)  COMMENT '显示状态',
  `menu_type_` char(1)  COMMENT '菜单类型',
  `path_` varchar(255)  COMMENT '访问路径',
  `component_` varchar(500)  COMMENT '引用组件',
  `icon_` varchar(255)  COMMENT '菜单图标',
  `tag_` varchar(2000)  COMMENT '标签',
  `classes_` varchar(2000)  COMMENT '类型',
  `sort_` int COMMENT '排序编号',
  `parent_id_` char(32)  COMMENT '上级权限标识',
  `create_by_` varchar(128)  COMMENT '创建人姓名',
  `create_time_` datetime COMMENT '创建时间',
  `update_by_` varchar(128)  COMMENT '修改人姓名',
  `update_time_` datetime COMMENT '修改时间',
  `owner_user_id_` varchar(128)  COMMENT '所属用户标识',
  `owner_user_account_` varchar(255)  COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128)  COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255)  COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255)  COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_menu_app_id`(`owner_app_id_` ASC) USING BTREE,
  INDEX `idx_menu_node_key`(`node_key_` ASC) USING BTREE,
  INDEX `idx_menu_key`(`key_` ASC) USING BTREE,
  INDEX `idx_menu_status`(`status_` ASC) USING BTREE,
  INDEX `idx_menu_parent_id`(`parent_id_` ASC) USING BTREE
)  COMMENT = '菜单权限' ;

-- ----------------------------
-- Table structure for sys_model_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_model_`  (
  `module_` char(25)  NOT NULL COMMENT '绑定功能',
  `editor_model_json_` longtext  NULL COMMENT '模型配置',
  PRIMARY KEY (`module_`) USING BTREE
)  COMMENT = '功能扩展模型' ;

-- ----------------------------
-- Table structure for sys_role_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_role_`  (
  `id_` char(32)  NOT NULL,
  `owner_app_id_` char(32)  COMMENT '所属应用标识',
  `key_` varchar(100)  COMMENT '角色标识',
  `name_` varchar(255)  COMMENT '角色名称',
  `status_` char(1)  COMMENT '角色状态',
  `desc_` varchar(2000)  COMMENT '角色描述',
  `default_grant_` char(1)  COMMENT '默认授予登录用户',
  `priority_` int COMMENT '优先级（多个角色存在冲突时以哪个角色为主）',
  `main_page_path_` varchar(255)  COMMENT '主页地址',
  `sort_` int COMMENT '排序编号',
  `create_by_` varchar(128)  COMMENT '创建人姓名',
  `create_time_` datetime COMMENT '创建时间',
  `update_by_` varchar(128)  COMMENT '修改人姓名',
  `update_time_` datetime COMMENT '修改时间',
  `owner_user_id_` varchar(128)  COMMENT '所属用户标识',
  `owner_user_account_` varchar(255)  COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128)  COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255)  COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255)  COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_role_app_id`(`owner_app_id_` ASC) USING BTREE,
  INDEX `idx_role_key`(`key_` ASC) USING BTREE,
  INDEX `idx_role_status`(`status_` ASC) USING BTREE
)  COMMENT = '角色管理' ;

-- ----------------------------
-- Table structure for sys_role_ex_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_role_ex_`  (
  `id_` bigint NOT NULL,
  `role_id_` bigint COMMENT '角色标识',
  `ex_role_id_` bigint COMMENT '相斥角色标识',
  PRIMARY KEY (`id_`) USING BTREE
)  COMMENT = '相斥角色' ;

-- ----------------------------
-- Table structure for sys_role_menu_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_role_menu_`  (
  `id_` char(32)  NOT NULL,
  `role_id_` char(32)  COMMENT '角色标识',
  `app_id_` char(32)  COMMENT '应用标识',
  `menu_id_` char(32)  COMMENT '菜单标识',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_role_menu_role_id`(`role_id_` ASC) USING BTREE,
  INDEX `idx_role_menu_app_id`(`app_id_` ASC) USING BTREE,
  INDEX `idx_role_menu_menu_id`(`menu_id_` ASC) USING BTREE
)  COMMENT = '角色菜单关联' ;

-- ----------------------------
-- Table structure for sys_user_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_user_`  (
  `id_` char(128)  NOT NULL,
  `dept_id_` char(128)  COMMENT '所属部门',
  `dept_identity_` varchar(100)  COMMENT '部门内身份',
  `username_` varchar(255)  COMMENT '账号',
  `password_` varchar(1000)  COMMENT '密码',
  `mask_v_` varchar(1000) ,
  `password_policy_` char(4)  COMMENT '密码策略',
  `password_expired_` date COMMENT '现有密码过期时间',
  `old_password_` varchar(1000)  COMMENT '旧密码',
  `nickname_` varchar(255)  COMMENT '昵称',
  `realname_` varchar(255)  COMMENT '真实姓名',
  `sex_` char(1)  COMMENT '性别',
  `card_type_` char(1)  COMMENT '证件类型',
  `card_no_` varchar(255)  COMMENT '证件号码',
  `phone_` varchar(50)  COMMENT '手机号码',
  `email_` varchar(255)  COMMENT '邮箱',
  `work_addr_` varchar(2000)  COMMENT '工作地点',
  `work_unit_` varchar(255)  COMMENT '工作单位',
  `work_pos_` varchar(255)  COMMENT '工作职务',
  `work_rank_` varchar(255)  COMMENT '工作职级',
  `home_addr_` varchar(2000)  COMMENT '家庭住址',
  `home_phone_` varchar(255)  COMMENT '家庭电话',
  `source_` char(1)  COMMENT '用户来源',
  `sort_` int COMMENT '排序编号',
  `status_` char(1)  COMMENT '用户状态',
  `account_lock_` char(1)  COMMENT '账户是否锁定',
  `last_change_password_` datetime COMMENT '上一次修改密码时间（这里配置了一段时间仍然可以使用旧密码来登录）',
  `create_by_` varchar(128)  COMMENT '创建人姓名',
  `create_time_` datetime COMMENT '创建时间',
  `update_by_` varchar(128)  COMMENT '修改人姓名',
  `update_time_` datetime COMMENT '修改时间',
  `owner_user_id_` varchar(128)  COMMENT '所属用户标识',
  `owner_user_account_` varchar(255)  COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128)  COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255)  COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255)  COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_user_dept_id`(`dept_id_` ASC) USING BTREE,
  INDEX `idx_user_username`(`username_` ASC) USING BTREE,
  INDEX `idx_user_status`(`status_` ASC) USING BTREE,
  INDEX `idx_user_account_lock`(`account_lock_` ASC) USING BTREE,
  INDEX `idx_user_phone`(`phone_` ASC) USING BTREE,
  INDEX `idx_user_email`(`email_` ASC) USING BTREE
)  COMMENT = '用户' ;

-- ----------------------------
-- Table structure for sys_user_ext_attr_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_user_ext_attr_`  (
  `id_` char(128)  NOT NULL COMMENT '用户标识，与用户一对一',
  `attribution_` json NULL COMMENT '扩展属性',
  PRIMARY KEY (`id_`) USING BTREE
)  ;

-- ----------------------------
-- Table structure for sys_user_manager_dept_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_user_manager_dept_`  (
  `id_` char(32)  NOT NULL COMMENT '标识',
  `user_id_` char(128)  COMMENT '用户标识',
  `dept_id_` char(128)  COMMENT '部门标识',
  `data_access_scope_` varchar(20)  COMMENT '数据可见范围',
  `contain_sub_` char(1)  COMMENT '是否包含子部门',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_user_manager_user_id`(`user_id_` ASC) USING BTREE,
  INDEX `idx_user_manager_dept_id`(`dept_id_` ASC) USING BTREE
)  COMMENT = '用户管辖组织机构' ;

-- ----------------------------
-- Table structure for sys_user_role_
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_user_role_`  (
  `id_` char(32)  NOT NULL,
  `user_id_` char(128) ,
  `role_id_` char(32) ,
  `app_id_` char(32) ,
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_user_role_user_id`(`user_id_` ASC) USING BTREE,
  INDEX `idx_user_role_role_id`(`role_id_` ASC) USING BTREE,
  INDEX `idx_user_role_app_id`(`app_id_` ASC) USING BTREE
)  ;