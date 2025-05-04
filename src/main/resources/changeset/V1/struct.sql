/*
 Navicat Premium Dump SQL

 Source Server         : （开发）管理
 Source Server Type    : MySQL
 Source Server Version : 80100 (8.1.0)
 Source Host           : dev-mysql.hljzj.tech:3306
 Source Schema         : frm_base_v1

 Target Server Type    : MySQL
 Target Server Version : 80100 (8.1.0)
 File Encoding         : 65001

 Date: 28/04/2025 13:13:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for databasechangelog
-- ----------------------------
DROP TABLE IF EXISTS `databasechangelog`;
CREATE TABLE `databasechangelog`  (
  `ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `AUTHOR` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `FILENAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `MD5SUM` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `COMMENTS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `TAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `LIQUIBASE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CONTEXTS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `LABELS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for databasechangeloglock
-- ----------------------------
DROP TABLE IF EXISTS `databasechangeloglock`;
CREATE TABLE `databasechangeloglock`  (
  `ID` int NOT NULL,
  `LOCKED` tinyint NOT NULL,
  `LOCKGRANTED` datetime NULL DEFAULT NULL,
  `LOCKEDBY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_app_
-- ----------------------------
DROP TABLE IF EXISTS `sys_app_`;
CREATE TABLE `sys_app_`  (
  `id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `key_` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用标识',
  `name_` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用名称',
  `main_page_path_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主页地址',
  `secret_` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用密钥',
  `status_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用状态',
  `desc_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用描述',
  `sort_` int NULL DEFAULT NULL COMMENT '排序编号',
  `create_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time_` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time_` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `owner_user_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户标识',
  `owner_user_account_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_app_key`(`key_` ASC) USING BTREE,
  INDEX `idx_app_status`(`status_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应用管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_config_
-- ----------------------------
DROP TABLE IF EXISTS `sys_config_`;
CREATE TABLE `sys_config_`  (
  `id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `owner_app_id_` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属应用标识',
  `key_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置标识',
  `name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置名称',
  `value_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置项值',
  `status_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置状态',
  `desc_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置描述',
  `sort_` int NULL DEFAULT NULL COMMENT '排序编号',
  `locked_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否锁定',
  `create_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time_` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time_` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `owner_user_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户标识',
  `owner_user_account_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_config_app_id`(`owner_app_id_` ASC) USING BTREE,
  INDEX `idx_config_key`(`key_` ASC) USING BTREE,
  INDEX `idx_config_status`(`status_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept_
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_`;
CREATE TABLE `sys_dept_`  (
  `id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `parent_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上级组织',
  `node_key_` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '本级节点标识',
  `node_path_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '祖先节点路径',
  `key_` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织标识',
  `name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `alias_` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织别名',
  `short_name_` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织简称',
  `management_relation_` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理关系',
  `type_` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织类型',
  `duty_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织责任说明',
  `usc_no_` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '统一社会信用代码',
  `addr_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在地点',
  `addr_no_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在地点编码',
  `status_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `enable_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否启用',
  `allow_user_join_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否允许用户加入',
  `sort_` int NULL DEFAULT NULL COMMENT '排序编号',
  `tmp_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否临时组织',
  `create_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time_` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time_` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `owner_user_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户标识',
  `owner_user_account_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户账号',
  `owner_dept_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门标识',
  `owner_dept_code_` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_dept_parent_id`(`parent_id_` ASC) USING BTREE,
  INDEX `idx_dept_node_key`(`node_key_` ASC) USING BTREE,
  INDEX `idx_dept_key`(`key_` ASC) USING BTREE,
  INDEX `idx_dept_status`(`status_` ASC) USING BTREE,
  INDEX `idx_dept_enable`(`enable_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组织机构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept_external_user_
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_external_user_`;
CREATE TABLE `sys_dept_external_user_`  (
  `id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `dept_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门标识',
  `user_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户标识',
  `identity_` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户身份',
  `remarks_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_dept_external_dept_id`(`dept_id_` ASC) USING BTREE,
  INDEX `idx_dept_external_user_id`(`user_id_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '外部编入人员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept_identity_
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_identity_`;
CREATE TABLE `sys_dept_identity_`  (
  `id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `dept_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门标识',
  `key_` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份标识',
  `name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份说明',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_dept_identity_dept_id`(`dept_id_` ASC) USING BTREE,
  INDEX `idx_dept_identity_key`(`key_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门内岗位身份设置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_data_
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data_`;
CREATE TABLE `sys_dict_data_`  (
  `id_` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `owner_app_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属应用标识',
  `owner_type_id_` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属字典类型标识',
  `key_` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典标识',
  `name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `value_` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典项值',
  `list_class_` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据样式',
  `selectable_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否可选',
  `status_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典状态',
  `help_message_` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选项说明',
  `desc_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典描述',
  `locked_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否锁定',
  `sort_` int NULL DEFAULT NULL COMMENT '排序编号',
  `create_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time_` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time_` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `owner_user_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户标识',
  `owner_user_account_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_dict_data_app_id`(`owner_app_id_` ASC) USING BTREE,
  INDEX `idx_dict_data_type_id`(`owner_type_id_` ASC) USING BTREE,
  INDEX `idx_dict_data_key`(`key_` ASC) USING BTREE,
  INDEX `idx_dict_data_status`(`status_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_type_
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type_`;
CREATE TABLE `sys_dict_type_`  (
  `id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `owner_app_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属应用标识',
  `key_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典类型标识',
  `name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典类型名称',
  `status_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典类型状态',
  `max_length_` int NULL DEFAULT NULL COMMENT '字典项值长度限制',
  `help_message_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典说明',
  `desc_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典类型描述',
  `sort_` int NULL DEFAULT NULL COMMENT '排序编号',
  `create_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time_` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time_` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `owner_user_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户标识',
  `owner_user_account_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_dict_type_app_id`(`owner_app_id_` ASC) USING BTREE,
  INDEX `idx_dict_type_key`(`key_` ASC) USING BTREE,
  INDEX `idx_dict_type_status`(`status_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log_
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_`;
CREATE TABLE `sys_log_`  (
  `id_` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `app_name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作应用',
  `module_name_` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `function_name_` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `oper_type_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `oper_content_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `error_code_` int NULL DEFAULT NULL COMMENT '错误状态代码',
  `addr_` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `params_` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '传入参数',
  `result_` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '返回数据',
  `client_ip_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端IP',
  `client_agent_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端终端',
  `op_app_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源应用',
  `op_user_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人ID',
  `op_user_username_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人账号',
  `op_user_realname_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人真实姓名',
  `op_org_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人所在部门',
  `op_org_name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人所在部门名称',
  `op_time_` datetime NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_log_app_name`(`app_name_` ASC) USING BTREE,
  INDEX `idx_log_op_user_id`(`op_user_id_` ASC) USING BTREE,
  INDEX `idx_log_op_time`(`op_time_` ASC) USING BTREE,
  INDEX `idx_log_oper_type`(`oper_type_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu_
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_`;
CREATE TABLE `sys_menu_`  (
  `id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `owner_app_id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属应用标识',
  `node_key_` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '节点标识',
  `node_path_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '节点路径',
  `key_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单标识，权限标识',
  `name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `status_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单状态',
  `desc_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单描述',
  `default_grant_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '默认授予登录用户',
  `visible_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示状态',
  `menu_type_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单类型',
  `path_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问路径',
  `component_` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '引用组件',
  `icon_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `tag_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
  `classes_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `sort_` int NULL DEFAULT NULL COMMENT '排序编号',
  `parent_id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上级权限标识',
  `create_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time_` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time_` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `owner_user_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户标识',
  `owner_user_account_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_menu_app_id`(`owner_app_id_` ASC) USING BTREE,
  INDEX `idx_menu_node_key`(`node_key_` ASC) USING BTREE,
  INDEX `idx_menu_key`(`key_` ASC) USING BTREE,
  INDEX `idx_menu_status`(`status_` ASC) USING BTREE,
  INDEX `idx_menu_parent_id`(`parent_id_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_model_
-- ----------------------------
DROP TABLE IF EXISTS `sys_model_`;
CREATE TABLE `sys_model_`  (
  `module_` char(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '绑定功能',
  `editor_model_json_` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '模型配置',
  PRIMARY KEY (`module_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '功能扩展模型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_`;
CREATE TABLE `sys_role_`  (
  `id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `owner_app_id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属应用标识',
  `key_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色标识',
  `name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `status_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色状态',
  `desc_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `default_grant_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '默认授予登录用户',
  `priority_` int NULL DEFAULT NULL COMMENT '优先级（多个角色存在冲突时以哪个角色为主）',
  `main_page_path_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主页地址',
  `sort_` int NULL DEFAULT NULL COMMENT '排序编号',
  `create_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time_` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time_` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `owner_user_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户标识',
  `owner_user_account_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_role_app_id`(`owner_app_id_` ASC) USING BTREE,
  INDEX `idx_role_key`(`key_` ASC) USING BTREE,
  INDEX `idx_role_status`(`status_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_ex_
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_ex_`;
CREATE TABLE `sys_role_ex_`  (
  `id_` bigint NOT NULL,
  `role_id_` bigint NULL DEFAULT NULL COMMENT '角色标识',
  `ex_role_id_` bigint NULL DEFAULT NULL COMMENT '相斥角色标识',
  PRIMARY KEY (`id_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '相斥角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu_
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_`;
CREATE TABLE `sys_role_menu_`  (
  `id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色标识',
  `app_id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用标识',
  `menu_id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单标识',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_role_menu_role_id`(`role_id_` ASC) USING BTREE,
  INDEX `idx_role_menu_app_id`(`app_id_` ASC) USING BTREE,
  INDEX `idx_role_menu_menu_id`(`menu_id_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_`;
CREATE TABLE `sys_user_`  (
  `id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `dept_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门',
  `dept_identity_` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门内身份',
  `username_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password_` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `mask_v_` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password_policy_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码策略',
  `old_password_` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '旧密码',
  `nickname_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `realname_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `sex_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `card_type_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `card_no_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `phone_` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `work_addr_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作地点',
  `work_unit_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作单位',
  `work_pos_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作职务',
  `work_rank_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作职级',
  `home_addr_` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `home_phone_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家庭电话',
  `source_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户来源',
  `sort_` int NULL DEFAULT NULL COMMENT '排序编号',
  `status_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户状态',
  `account_lock_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账户是否锁定',
  `last_change_password_` datetime NULL DEFAULT NULL COMMENT '上一次修改密码时间（这里配置了一段时间仍然可以使用旧密码来登录）',
  `create_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_time_` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  `update_time_` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `owner_user_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户标识',
  `owner_user_account_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户账号',
  `owner_dept_id_` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门标识',
  `owner_dept_code_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门编码',
  `owner_dept_name_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门名称',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_user_dept_id`(`dept_id_` ASC) USING BTREE,
  INDEX `idx_user_username`(`username_` ASC) USING BTREE,
  INDEX `idx_user_status`(`status_` ASC) USING BTREE,
  INDEX `idx_user_account_lock`(`account_lock_` ASC) USING BTREE,
  INDEX `idx_user_phone`(`phone_` ASC) USING BTREE,
  INDEX `idx_user_email`(`email_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_ext_attr_
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_ext_attr_`;
CREATE TABLE `sys_user_ext_attr_`  (
  `id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户标识，与用户一对一',
  `attribution_` json NULL COMMENT '扩展属性',
  PRIMARY KEY (`id_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_manager_dept_
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_manager_dept_`;
CREATE TABLE `sys_user_manager_dept_`  (
  `id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标识',
  `user_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户标识',
  `dept_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门标识',
  `data_access_scope_` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据可见范围',
  `contain_sub_` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否包含子部门',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_user_manager_user_id`(`user_id_` ASC) USING BTREE,
  INDEX `idx_user_manager_dept_id`(`dept_id_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户管辖组织机构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role_
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_`;
CREATE TABLE `sys_user_role_`  (
  `id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id_` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role_id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `app_id_` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `idx_user_role_user_id`(`user_id_` ASC) USING BTREE,
  INDEX `idx_user_role_role_id`(`role_id_` ASC) USING BTREE,
  INDEX `idx_user_role_app_id`(`app_id_` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for v_sys_dept_member_user_
-- ----------------------------
DROP VIEW IF EXISTS `v_sys_dept_member_user_`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_sys_dept_member_user_` AS select `v_sys_user_`.`id_` AS `id_`,`v_sys_user_`.`dept_id_` AS `dept_id_`,`v_sys_user_`.`dept_identity_` AS `dept_identity_`,`v_sys_user_`.`username_` AS `username_`,`v_sys_user_`.`password_` AS `password_`,`v_sys_user_`.`mask_v_` AS `mask_v_`,`v_sys_user_`.`old_password_` AS `old_password_`,`v_sys_user_`.`nickname_` AS `nickname_`,`v_sys_user_`.`realname_` AS `realname_`,`v_sys_user_`.`sex_` AS `sex_`,`v_sys_user_`.`card_type_` AS `card_type_`,`v_sys_user_`.`card_no_` AS `card_no_`,`v_sys_user_`.`phone_` AS `phone_`,`v_sys_user_`.`email_` AS `email_`,`v_sys_user_`.`work_addr_` AS `work_addr_`,`v_sys_user_`.`work_unit_` AS `work_unit_`,`v_sys_user_`.`work_pos_` AS `work_pos_`,`v_sys_user_`.`work_rank_` AS `work_rank_`,`v_sys_user_`.`home_addr_` AS `home_addr_`,`v_sys_user_`.`home_phone_` AS `home_phone_`,`v_sys_user_`.`source_` AS `source_`,`v_sys_user_`.`sort_` AS `sort_`,`v_sys_user_`.`status_` AS `status_`,`v_sys_user_`.`account_lock_` AS `account_lock_`,`v_sys_user_`.`owner_user_id_` AS `owner_user_id_`,`v_sys_user_`.`owner_user_account_` AS `owner_user_account_`,`v_sys_user_`.`owner_dept_id_` AS `owner_dept_id_`,`v_sys_user_`.`owner_dept_code_` AS `owner_dept_code_`,`v_sys_user_`.`owner_dept_name_` AS `owner_dept_name_`,`v_sys_user_`.`create_by_` AS `create_by_`,`v_sys_user_`.`create_time_` AS `create_time_`,`v_sys_user_`.`update_by_` AS `update_by_`,`v_sys_user_`.`update_time_` AS `update_time_`,`v_sys_user_`.`last_change_password_` AS `last_change_password_`,`v_sys_user_`.`node_key_` AS `node_key_`,`v_sys_user_`.`node_path_` AS `node_path_`,`v_sys_user_`.`dept_key_` AS `dept_key_`,`v_sys_user_`.`dept_name_` AS `dept_name_`,`v_sys_user_`.`dept_alias_` AS `dept_alias_`,`v_sys_user_`.`dept_short_name_` AS `dept_short_name_`,`v_sys_user_`.`dept_type_` AS `dept_type_`,`v_sys_user_`.`dept_usc_no_` AS `dept_usc_no_`,`v_sys_user_`.`dept_addr_no_` AS `dept_addr_no_`,1 AS `owner_type` from `v_sys_user_` union all select `t`.`id_` AS `id_`,`t1`.`id_` AS `dept_id_`,`p`.`identity_` AS `dept_identity_`,`t`.`username_` AS `username_`,`t`.`password_` AS `password_`,`t`.`mask_v_` AS `mask_v_`,`t`.`old_password_` AS `old_password_`,`t`.`nickname_` AS `nickname_`,`t`.`realname_` AS `realname_`,`t`.`sex_` AS `sex_`,`t`.`card_type_` AS `card_type_`,`t`.`card_no_` AS `card_no_`,`t`.`phone_` AS `phone_`,`t`.`email_` AS `email_`,`t`.`work_addr_` AS `work_addr_`,`t`.`work_unit_` AS `work_unit_`,`t`.`work_pos_` AS `work_pos_`,`t`.`work_rank_` AS `work_rank_`,`t`.`home_addr_` AS `home_addr_`,`t`.`home_phone_` AS `home_phone_`,`t`.`source_` AS `source_`,`t`.`sort_` AS `sort_`,`t`.`status_` AS `status_`,`t`.`account_lock_` AS `account_lock_`,`t`.`owner_user_id_` AS `owner_user_id_`,`t`.`owner_user_account_` AS `owner_user_account_`,`t`.`owner_dept_id_` AS `owner_dept_id_`,`t`.`owner_dept_code_` AS `owner_dept_code_`,`t`.`owner_dept_name_` AS `owner_dept_name_`,`t`.`create_by_` AS `create_by_`,`t`.`create_time_` AS `create_time_`,`t`.`update_by_` AS `update_by_`,`t`.`update_time_` AS `update_time_`,`t`.`last_change_password_` AS `last_change_password_`,`t1`.`node_key_` AS `node_key_`,`t1`.`node_path_` AS `node_path_`,`t1`.`key_` AS `dept_key_`,`t1`.`name_` AS `dept_name_`,`t1`.`alias_` AS `dept_alias_`,`t1`.`short_name_` AS `dept_short_name_`,`t1`.`type_` AS `dept_type_`,`t1`.`usc_no_` AS `dept_usc_no_`,`t1`.`addr_no_` AS `dept_addr_no_`,2 AS `owner_type` from ((`sys_dept_external_user_` `p` left join `sys_user_` `t` on((`t`.`id_` = `p`.`user_id_`))) left join `sys_dept_` `t1` on((`t1`.`id_` = `p`.`dept_id_`)));

-- ----------------------------
-- View structure for v_sys_user_
-- ----------------------------
DROP VIEW IF EXISTS `v_sys_user_`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_sys_user_` AS select `t`.`id_` AS `id_`,`t`.`dept_id_` AS `dept_id_`,`t`.`dept_identity_` AS `dept_identity_`,`t`.`username_` AS `username_`,`t`.`password_` AS `password_`,`t`.`mask_v_` AS `mask_v_`,`t`.`old_password_` AS `old_password_`,`t`.`nickname_` AS `nickname_`,`t`.`realname_` AS `realname_`,`t`.`sex_` AS `sex_`,`t`.`card_type_` AS `card_type_`,`t`.`card_no_` AS `card_no_`,`t`.`phone_` AS `phone_`,`t`.`email_` AS `email_`,`t`.`work_addr_` AS `work_addr_`,`t`.`work_unit_` AS `work_unit_`,`t`.`work_pos_` AS `work_pos_`,`t`.`work_rank_` AS `work_rank_`,`t`.`home_addr_` AS `home_addr_`,`t`.`home_phone_` AS `home_phone_`,`t`.`source_` AS `source_`,`t`.`sort_` AS `sort_`,`t`.`status_` AS `status_`,`t`.`account_lock_` AS `account_lock_`,`t`.`owner_user_id_` AS `owner_user_id_`,`t`.`owner_user_account_` AS `owner_user_account_`,`t`.`owner_dept_id_` AS `owner_dept_id_`,`t`.`owner_dept_code_` AS `owner_dept_code_`,`t`.`owner_dept_name_` AS `owner_dept_name_`,`t`.`create_by_` AS `create_by_`,`t`.`create_time_` AS `create_time_`,`t`.`update_by_` AS `update_by_`,`t`.`update_time_` AS `update_time_`,`t`.`last_change_password_` AS `last_change_password_`,`t1`.`node_key_` AS `node_key_`,`t1`.`node_path_` AS `node_path_`,`t1`.`key_` AS `dept_key_`,`t1`.`name_` AS `dept_name_`,`t1`.`alias_` AS `dept_alias_`,`t1`.`short_name_` AS `dept_short_name_`,`t1`.`type_` AS `dept_type_`,`t1`.`usc_no_` AS `dept_usc_no_`,`t1`.`addr_no_` AS `dept_addr_no_` from (`sys_user_` `t` left join `sys_dept_` `t1` on((`t1`.`id_` = `t`.`dept_id_`)));

-- ----------------------------
-- Procedure structure for update_dept_path
-- ----------------------------
DROP PROCEDURE IF EXISTS `update_dept_path`;
delimiter ;;
CREATE DEFINER=`root`@`%` PROCEDURE `update_dept_path`()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE dept_id VARCHAR(32);
    DECLARE parent_id VARCHAR(32);
    DECLARE dept_name VARCHAR(100);
    DECLARE cur_level INT;
    DECLARE cur_path VARCHAR(1000);
    DECLARE cur_key VARCHAR(32);
    
    -- 确保临时表被正确清理
    DROP TEMPORARY TABLE IF EXISTS temp_dept_result;
    
    -- 创建临时表存储处理结果
    CREATE TEMPORARY TABLE temp_dept_result (
        id_ VARCHAR(32),
        parent_id_ VARCHAR(32),
        name_ VARCHAR(100),
        level_ INT,
        node_key_ VARCHAR(32),
        node_path_ VARCHAR(1000)
    ) ENGINE=InnoDB;
    
    -- 处理根部门
    INSERT INTO temp_dept_result
    SELECT 
        id_,
        parent_id_,
        name_,
        1,
        SUBSTRING(MD5(RAND()), 1, 16),
        CONCAT('/', SUBSTRING(MD5(RAND()), 1, 16))
    FROM frm_base_v1.sys_dept_
    WHERE parent_id_ = '0';
    
    -- 处理子部门
    SET cur_level = 2;
    WHILE EXISTS (
        SELECT 1 FROM frm_base_v1.sys_dept_ d
        WHERE d.parent_id_ IN (SELECT id_ FROM temp_dept_result WHERE level_ = cur_level - 1)
        AND d.id_ NOT IN (SELECT id_ FROM temp_dept_result)
    ) DO
        INSERT INTO temp_dept_result
        SELECT 
            d.id_,
            d.parent_id_,
            d.name_,
            cur_level,
            SUBSTRING(MD5(RAND()), 1, 16),
            CONCAT(p.node_path_, '/', SUBSTRING(MD5(RAND()), 1, 16))
        FROM frm_base_v1.sys_dept_ d
        JOIN temp_dept_result p ON d.parent_id_ = p.id_
        WHERE p.level_ = cur_level - 1
        AND d.id_ NOT IN (SELECT id_ FROM temp_dept_result);
        
        SET cur_level = cur_level + 1;
    END WHILE;
    
    -- 更新部门表
    UPDATE frm_base_v1.sys_dept_ d
    JOIN temp_dept_result t ON d.id_ = t.id_
    SET d.node_key_ = t.node_key_,
        d.node_path_ = t.node_path_;
    
    -- 清理临时表
    DROP TEMPORARY TABLE IF EXISTS temp_dept_result;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
