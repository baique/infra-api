CREATE TABLE IF NOT EXISTS sys_app_ (
                                        id_ CHAR(32) NOT NULL,
    key_ VARCHAR(100), -- 应用标识
    name_ VARCHAR(125), -- 应用名称
    main_page_path_ VARCHAR(255), -- 主页地址
    secret_ VARCHAR(50), -- 应用密钥
    status_ CHAR(1), -- 应用状态
    desc_ VARCHAR(2000), -- 应用描述
    sort_ INTEGER, -- 排序编号
    create_by_ VARCHAR(128), -- 创建人姓名
    create_time_ TIMESTAMP, -- 创建时间
    update_by_ VARCHAR(128), -- 修改人姓名
    update_time_ TIMESTAMP, -- 修改时间
    owner_user_id_ VARCHAR(128), -- 所属用户标识
    owner_user_account_ VARCHAR(255), -- 所属用户账号
    owner_dept_id_ VARCHAR(128), -- 所属部门标识
    owner_dept_code_ VARCHAR(255), -- 所属部门编码
    owner_dept_name_ VARCHAR(255), -- 所属部门名称
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_app_key ON sys_app_ (key_);
CREATE INDEX idx_app_status ON sys_app_ (status_);
-- 表注释（PostgreSQL 需用COMMENT ON TABLE和COMMENT ON COLUMN）
COMMENT ON TABLE sys_app_ IS '应用管理';
COMMENT ON COLUMN sys_app_.key_ IS '应用标识';
COMMENT ON COLUMN sys_app_.name_ IS '应用名称';
COMMENT ON COLUMN sys_app_.main_page_path_ IS '主页地址';
COMMENT ON COLUMN sys_app_.secret_ IS '应用密钥';
COMMENT ON COLUMN sys_app_.status_ IS '应用状态';
COMMENT ON COLUMN sys_app_.desc_ IS '应用描述';
COMMENT ON COLUMN sys_app_.sort_ IS '排序编号';
COMMENT ON COLUMN sys_app_.create_by_ IS '创建人姓名';
COMMENT ON COLUMN sys_app_.create_time_ IS '创建时间';
COMMENT ON COLUMN sys_app_.update_by_ IS '修改人姓名';
COMMENT ON COLUMN sys_app_.update_time_ IS '修改时间';
COMMENT ON COLUMN sys_app_.owner_user_id_ IS '所属用户标识';
COMMENT ON COLUMN sys_app_.owner_user_account_ IS '所属用户账号';
COMMENT ON COLUMN sys_app_.owner_dept_id_ IS '所属部门标识';
COMMENT ON COLUMN sys_app_.owner_dept_code_ IS '所属部门编码';
COMMENT ON COLUMN sys_app_.owner_dept_name_ IS '所属部门名称';
CREATE TABLE IF NOT EXISTS sys_config_ (
                                           id_ CHAR(32) NOT NULL,
    owner_app_id_ CHAR(64), -- 所属应用标识
    key_ VARCHAR(100), -- 配置标识
    name_ VARCHAR(255), -- 配置名称
    value_ VARCHAR(255), -- 配置项值
    status_ CHAR(1), -- 配置状态
    desc_ VARCHAR(2000), -- 配置描述
    sort_ INTEGER, -- 排序编号
    locked_ CHAR(1), -- 是否锁定
    create_by_ VARCHAR(128), -- 创建人姓名
    create_time_ TIMESTAMP, -- 创建时间
    update_by_ VARCHAR(128), -- 修改人姓名
    update_time_ TIMESTAMP, -- 修改时间
    owner_user_id_ VARCHAR(128), -- 所属用户标识
    owner_user_account_ VARCHAR(255), -- 所属用户账号
    owner_dept_id_ VARCHAR(128), -- 所属部门标识
    owner_dept_code_ VARCHAR(255), -- 所属部门编码
    owner_dept_name_ VARCHAR(255), -- 所属部门名称
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_config_app_id ON sys_config_ (owner_app_id_);
CREATE INDEX idx_config_key ON sys_config_ (key_);
CREATE INDEX idx_config_status ON sys_config_ (status_);

COMMENT ON TABLE sys_config_ IS '系统配置';
COMMENT ON COLUMN sys_config_.owner_app_id_ IS '所属应用标识';
COMMENT ON COLUMN sys_config_.key_ IS '配置标识';
COMMENT ON COLUMN sys_config_.name_ IS '配置名称';
COMMENT ON COLUMN sys_config_.value_ IS '配置项值';
COMMENT ON COLUMN sys_config_.status_ IS '配置状态';
COMMENT ON COLUMN sys_config_.desc_ IS '配置描述';
COMMENT ON COLUMN sys_config_.sort_ IS '排序编号';
COMMENT ON COLUMN sys_config_.locked_ IS '是否锁定';
COMMENT ON COLUMN sys_config_.create_by_ IS '创建人姓名';
COMMENT ON COLUMN sys_config_.create_time_ IS '创建时间';
COMMENT ON COLUMN sys_config_.update_by_ IS '修改人姓名';
COMMENT ON COLUMN sys_config_.update_time_ IS '修改时间';
COMMENT ON COLUMN sys_config_.owner_user_id_ IS '所属用户标识';
COMMENT ON COLUMN sys_config_.owner_user_account_ IS '所属用户账号';
COMMENT ON COLUMN sys_config_.owner_dept_id_ IS '所属部门标识';
COMMENT ON COLUMN sys_config_.owner_dept_code_ IS '所属部门编码';
COMMENT ON COLUMN sys_config_.owner_dept_name_ IS '所属部门名称';
CREATE TABLE IF NOT EXISTS sys_dept_ (
                                         id_ CHAR(128) NOT NULL,
    parent_id_ CHAR(128), -- 上级组织
    node_key_ CHAR(16), -- 本级节点标识
    node_path_ VARCHAR(2000), -- 祖先节点路径
    key_ VARCHAR(100), -- 组织标识
    name_ VARCHAR(255), -- 组织名称
    alias_ VARCHAR(100), -- 组织别名
    short_name_ VARCHAR(100), -- 组织简称
    management_relation_ VARCHAR(20), -- 管理关系
    type_ VARCHAR(20), -- 组织类型
    duty_ VARCHAR(2000), -- 组织责任说明
    usc_no_ VARCHAR(100), -- 统一社会信用代码
    addr_ VARCHAR(2000), -- 所在地点
    addr_no_ VARCHAR(255), -- 所在地点编码
    status_ CHAR(1), -- 状态
    enable_ CHAR(1), -- 是否启用
    allow_user_join_ CHAR(1), -- 是否允许用户加入
    sort_ INTEGER, -- 排序编号
    tmp_ CHAR(1), -- 是否临时组织
    create_by_ VARCHAR(128), -- 创建人姓名
    create_time_ TIMESTAMP, -- 创建时间
    update_by_ VARCHAR(128), -- 修改人姓名
    update_time_ TIMESTAMP, -- 修改时间
    owner_user_id_ CHAR(128), -- 所属用户标识
    owner_user_account_ VARCHAR(255), -- 所属用户账号
    owner_dept_id_ CHAR(128), -- 所属部门标识
    owner_dept_code_ VARCHAR(100), -- 所属部门编码
    owner_dept_name_ VARCHAR(255), -- 所属部门名称
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_dept_parent_id ON sys_dept_ (parent_id_);
CREATE INDEX idx_dept_node_key ON sys_dept_ (node_key_);
CREATE INDEX idx_dept_key ON sys_dept_ (key_);
CREATE INDEX idx_dept_status ON sys_dept_ (status_);
CREATE INDEX idx_dept_enable ON sys_dept_ (enable_);

COMMENT ON TABLE sys_dept_ IS '组织机构';
COMMENT ON COLUMN sys_dept_.parent_id_ IS '上级组织';
COMMENT ON COLUMN sys_dept_.node_key_ IS '本级节点标识';
COMMENT ON COLUMN sys_dept_.node_path_ IS '祖先节点路径';
COMMENT ON COLUMN sys_dept_.key_ IS '组织标识';
COMMENT ON COLUMN sys_dept_.name_ IS '组织名称';
COMMENT ON COLUMN sys_dept_.alias_ IS '组织别名';
COMMENT ON COLUMN sys_dept_.short_name_ IS '组织简称';
COMMENT ON COLUMN sys_dept_.management_relation_ IS '管理关系';
COMMENT ON COLUMN sys_dept_.type_ IS '组织类型';
COMMENT ON COLUMN sys_dept_.duty_ IS '组织责任说明';
COMMENT ON COLUMN sys_dept_.usc_no_ IS '统一社会信用代码';
COMMENT ON COLUMN sys_dept_.addr_ IS '所在地点';
COMMENT ON COLUMN sys_dept_.addr_no_ IS '所在地点编码';
COMMENT ON COLUMN sys_dept_.status_ IS '状态';
COMMENT ON COLUMN sys_dept_.enable_ IS '是否启用';
COMMENT ON COLUMN sys_dept_.allow_user_join_ IS '是否允许用户加入';
COMMENT ON COLUMN sys_dept_.sort_ IS '排序编号';
COMMENT ON COLUMN sys_dept_.tmp_ IS '是否临时组织';
COMMENT ON COLUMN sys_dept_.create_by_ IS '创建人姓名';
COMMENT ON COLUMN sys_dept_.create_time_ IS '创建时间';
COMMENT ON COLUMN sys_dept_.update_by_ IS '修改人姓名';
COMMENT ON COLUMN sys_dept_.update_time_ IS '修改时间';
COMMENT ON COLUMN sys_dept_.owner_user_id_ IS '所属用户标识';
COMMENT ON COLUMN sys_dept_.owner_user_account_ IS '所属用户账号';
COMMENT ON COLUMN sys_dept_.owner_dept_id_ IS '所属部门标识';
COMMENT ON COLUMN sys_dept_.owner_dept_code_ IS '所属部门编码';
COMMENT ON COLUMN sys_dept_.owner_dept_name_ IS '所属部门名称';
CREATE TABLE IF NOT EXISTS sys_dept_external_user_ (
                                                       id_ CHAR(32) NOT NULL,
    dept_id_ CHAR(128), -- 部门标识
    user_id_ CHAR(128), -- 用户标识
    identity_ VARCHAR(100), -- 用户身份
    remarks_ VARCHAR(2000), -- 备注信息
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_dept_external_dept_id ON sys_dept_external_user_ (dept_id_);
CREATE INDEX idx_dept_external_user_id ON sys_dept_external_user_ (user_id_);

COMMENT ON TABLE sys_dept_external_user_ IS '外部编入人员';
COMMENT ON COLUMN sys_dept_external_user_.dept_id_ IS '部门标识';
COMMENT ON COLUMN sys_dept_external_user_.user_id_ IS '用户标识';
COMMENT ON COLUMN sys_dept_external_user_.identity_ IS '用户身份';
COMMENT ON COLUMN sys_dept_external_user_.remarks_ IS '备注信息';
CREATE TABLE IF NOT EXISTS sys_dept_identity_ (
                                                  id_ CHAR(32) NOT NULL,
    dept_id_ CHAR(128), -- 部门标识
    key_ VARCHAR(100), -- 身份标识
    name_ VARCHAR(255), -- 身份说明
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_dept_identity_dept_id ON sys_dept_identity_ (dept_id_);
CREATE INDEX idx_dept_identity_key ON sys_dept_identity_ (key_);

COMMENT ON TABLE sys_dept_identity_ IS '部门内岗位身份设置';
COMMENT ON COLUMN sys_dept_identity_.dept_id_ IS '部门标识';
COMMENT ON COLUMN sys_dept_identity_.key_ IS '身份标识';
COMMENT ON COLUMN sys_dept_identity_.name_ IS '身份说明';
CREATE TABLE IF NOT EXISTS sys_dict_data_ (
                                              id_ CHAR(64) NOT NULL,
    owner_app_id_ CHAR(128), -- 所属应用标识
    owner_type_id_ CHAR(64), -- 所属字典类型标识
    key_ VARCHAR(100), -- 字典标识
    name_ VARCHAR(255), -- 字典名称
    value_ VARCHAR(500), -- 字典项值
    list_class_ VARCHAR(50), -- 数据样式
    selectable_ CHAR(1), -- 是否可选
    status_ CHAR(1), -- 字典状态
    help_message_ VARCHAR(500), -- 选项说明
    desc_ VARCHAR(2000), -- 字典描述
    locked_ CHAR(1), -- 是否锁定
    sort_ INTEGER, -- 排序编号
    create_by_ VARCHAR(128), -- 创建人姓名
    create_time_ TIMESTAMP, -- 创建时间
    update_by_ VARCHAR(128), -- 修改人姓名
    update_time_ TIMESTAMP, -- 修改时间
    owner_user_id_ VARCHAR(128), -- 所属用户标识
    owner_user_account_ VARCHAR(255), -- 所属用户账号
    owner_dept_id_ VARCHAR(128), -- 所属部门标识
    owner_dept_code_ VARCHAR(255), -- 所属部门编码
    owner_dept_name_ VARCHAR(255), -- 所属部门名称
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_dict_data_app_id ON sys_dict_data_ (owner_app_id_);
CREATE INDEX idx_dict_data_type_id ON sys_dict_data_ (owner_type_id_);
CREATE INDEX idx_dict_data_key ON sys_dict_data_ (key_);
CREATE INDEX idx_dict_data_status ON sys_dict_data_ (status_);

COMMENT ON TABLE sys_dict_data_ IS '字典项';
COMMENT ON COLUMN sys_dict_data_.owner_app_id_ IS '所属应用标识';
COMMENT ON COLUMN sys_dict_data_.owner_type_id_ IS '所属字典类型标识';
COMMENT ON COLUMN sys_dict_data_.key_ IS '字典标识';
COMMENT ON COLUMN sys_dict_data_.name_ IS '字典名称';
COMMENT ON COLUMN sys_dict_data_.value_ IS '字典项值';
COMMENT ON COLUMN sys_dict_data_.list_class_ IS '数据样式';
COMMENT ON COLUMN sys_dict_data_.selectable_ IS '是否可选';
COMMENT ON COLUMN sys_dict_data_.status_ IS '字典状态';
COMMENT ON COLUMN sys_dict_data_.help_message_ IS '选项说明';
COMMENT ON COLUMN sys_dict_data_.desc_ IS '字典描述';
COMMENT ON COLUMN sys_dict_data_.locked_ IS '是否锁定';
COMMENT ON COLUMN sys_dict_data_.sort_ IS '排序编号';
COMMENT ON COLUMN sys_dict_data_.create_by_ IS '创建人姓名';
COMMENT ON COLUMN sys_dict_data_.create_time_ IS '创建时间';
COMMENT ON COLUMN sys_dict_data_.update_by_ IS '修改人姓名';
COMMENT ON COLUMN sys_dict_data_.update_time_ IS '修改时间';
COMMENT ON COLUMN sys_dict_data_.owner_user_id_ IS '所属用户标识';
COMMENT ON COLUMN sys_dict_data_.owner_user_account_ IS '所属用户账号';
COMMENT ON COLUMN sys_dict_data_.owner_dept_id_ IS '所属部门标识';
COMMENT ON COLUMN sys_dict_data_.owner_dept_code_ IS '所属部门编码';
COMMENT ON COLUMN sys_dict_data_.owner_dept_name_ IS '所属部门名称';
CREATE TABLE IF NOT EXISTS sys_dict_type_ (
                                              id_ CHAR(32) NOT NULL,
    owner_app_id_ CHAR(128), -- 所属应用标识
    key_ VARCHAR(100), -- 字典类型标识
    name_ VARCHAR(255), -- 字典类型名称
    status_ CHAR(1), -- 字典类型状态
    max_length_ INTEGER, -- 字典项值长度限制
    help_message_ VARCHAR(255), -- 字典说明
    desc_ VARCHAR(2000), -- 字典类型描述
    sort_ INTEGER, -- 排序编号
    create_by_ VARCHAR(128), -- 创建人姓名
    create_time_ TIMESTAMP, -- 创建时间
    update_by_ VARCHAR(128), -- 修改人姓名
    update_time_ TIMESTAMP, -- 修改时间
    owner_user_id_ VARCHAR(128), -- 所属用户标识
    owner_user_account_ VARCHAR(255), -- 所属用户账号
    owner_dept_id_ VARCHAR(128), -- 所属部门标识
    owner_dept_code_ VARCHAR(255), -- 所属部门编码
    owner_dept_name_ VARCHAR(255), -- 所属部门名称
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_dict_type_app_id ON sys_dict_type_ (owner_app_id_);
CREATE INDEX idx_dict_type_key ON sys_dict_type_ (key_);
CREATE INDEX idx_dict_type_status ON sys_dict_type_ (status_);

COMMENT ON TABLE sys_dict_type_ IS '字典组';
COMMENT ON COLUMN sys_dict_type_.owner_app_id_ IS '所属应用标识';
COMMENT ON COLUMN sys_dict_type_.key_ IS '字典类型标识';
COMMENT ON COLUMN sys_dict_type_.name_ IS '字典类型名称';
COMMENT ON COLUMN sys_dict_type_.status_ IS '字典类型状态';
COMMENT ON COLUMN sys_dict_type_.max_length_ IS '字典项值长度限制';
COMMENT ON COLUMN sys_dict_type_.help_message_ IS '字典说明';
COMMENT ON COLUMN sys_dict_type_.desc_ IS '字典类型描述';
COMMENT ON COLUMN sys_dict_type_.sort_ IS '排序编号';
COMMENT ON COLUMN sys_dict_type_.create_by_ IS '创建人姓名';
COMMENT ON COLUMN sys_dict_type_.create_time_ IS '创建时间';
COMMENT ON COLUMN sys_dict_type_.update_by_ IS '修改人姓名';
COMMENT ON COLUMN sys_dict_type_.update_time_ IS '修改时间';
COMMENT ON COLUMN sys_dict_type_.owner_user_id_ IS '所属用户标识';
COMMENT ON COLUMN sys_dict_type_.owner_user_account_ IS '所属用户账号';
COMMENT ON COLUMN sys_dict_type_.owner_dept_id_ IS '所属部门标识';
COMMENT ON COLUMN sys_dict_type_.owner_dept_code_ IS '所属部门编码';
COMMENT ON COLUMN sys_dict_type_.owner_dept_name_ IS '所属部门名称';
CREATE TABLE IF NOT EXISTS sys_log_ (
                                        id_ CHAR(64) NOT NULL,
    app_name_ VARCHAR(255), -- 操作应用
    module_name_ VARCHAR(500), -- 模块名称
    function_name_ VARCHAR(500), -- 功能名称
    oper_type_ VARCHAR(255), -- 操作类型
    oper_content_ VARCHAR(2000), -- 操作描述
    error_code_ INTEGER, -- 错误状态代码
    addr_ VARCHAR(500), -- 请求地址
    params_ TEXT, -- 传入参数
    result_ TEXT, -- 返回数据
    client_ip_ VARCHAR(255), -- 客户端IP
    client_agent_ VARCHAR(255), -- 客户端终端
    op_app_id_ CHAR(128), -- 来源应用
    op_user_id_ CHAR(128), -- 操作人ID
    op_user_username_ VARCHAR(255), -- 操作人账号
    op_user_realname_ VARCHAR(255), -- 操作人真实姓名
    op_org_id_ CHAR(128), -- 操作人所在部门
    op_org_name_ VARCHAR(255), -- 操作人所在部门名称
    op_time_ TIMESTAMP, -- 操作时间
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_log_app_name ON sys_log_ (app_name_);
CREATE INDEX idx_log_op_user_id ON sys_log_ (op_user_id_);
CREATE INDEX idx_log_op_time ON sys_log_ (op_time_);
CREATE INDEX idx_log_oper_type ON sys_log_ (oper_type_);

COMMENT ON TABLE sys_log_ IS '操作日志';
COMMENT ON COLUMN sys_log_.app_name_ IS '操作应用';
COMMENT ON COLUMN sys_log_.module_name_ IS '模块名称';
COMMENT ON COLUMN sys_log_.function_name_ IS '功能名称';
COMMENT ON COLUMN sys_log_.oper_type_ IS '操作类型';
COMMENT ON COLUMN sys_log_.oper_content_ IS '操作描述';
COMMENT ON COLUMN sys_log_.error_code_ IS '错误状态代码';
COMMENT ON COLUMN sys_log_.addr_ IS '请求地址';
COMMENT ON COLUMN sys_log_.params_ IS '传入参数';
COMMENT ON COLUMN sys_log_.result_ IS '返回数据';
COMMENT ON COLUMN sys_log_.client_ip_ IS '客户端IP';
COMMENT ON COLUMN sys_log_.client_agent_ IS '客户端终端';
COMMENT ON COLUMN sys_log_.op_app_id_ IS '来源应用';
COMMENT ON COLUMN sys_log_.op_user_id_ IS '操作人ID';
COMMENT ON COLUMN sys_log_.op_user_username_ IS '操作人账号';
COMMENT ON COLUMN sys_log_.op_user_realname_ IS '操作人真实姓名';
COMMENT ON COLUMN sys_log_.op_org_id_ IS '操作人所在部门';
COMMENT ON COLUMN sys_log_.op_org_name_ IS '操作人所在部门名称';
COMMENT ON COLUMN sys_log_.op_time_ IS '操作时间';
CREATE TABLE IF NOT EXISTS sys_menu_ (
                                         id_ CHAR(32) NOT NULL,
    owner_app_id_ CHAR(32), -- 所属应用标识
    node_key_ CHAR(16), -- 节点标识
    node_path_ VARCHAR(2000), -- 节点路径
    key_ VARCHAR(100), -- 菜单标识，权限标识
    name_ VARCHAR(255), -- 菜单名称
    status_ CHAR(1), -- 菜单状态
    desc_ VARCHAR(2000), -- 菜单描述
    default_grant_ CHAR(1), -- 默认授予登录用户
    visible_ CHAR(1), -- 显示状态
    menu_type_ CHAR(1), -- 菜单类型
    path_ VARCHAR(255), -- 访问路径
    component_ VARCHAR(500), -- 引用组件
    icon_ VARCHAR(255), -- 菜单图标
    tag_ VARCHAR(2000), -- 标签
    classes_ VARCHAR(2000), -- 类型
    sort_ INTEGER, -- 排序编号
    parent_id_ CHAR(32), -- 上级权限标识
    create_by_ VARCHAR(128), -- 创建人姓名
    create_time_ TIMESTAMP, -- 创建时间
    update_by_ VARCHAR(128), -- 修改人姓名
    update_time_ TIMESTAMP, -- 修改时间
    owner_user_id_ VARCHAR(128), -- 所属用户标识
    owner_user_account_ VARCHAR(255), -- 所属用户账号
    owner_dept_id_ VARCHAR(128), -- 所属部门标识
    owner_dept_code_ VARCHAR(255), -- 所属部门编码
    owner_dept_name_ VARCHAR(255), -- 所属部门名称
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_menu_app_id ON sys_menu_ (owner_app_id_);
CREATE INDEX idx_menu_node_key ON sys_menu_ (node_key_);
CREATE INDEX idx_menu_key ON sys_menu_ (key_);
CREATE INDEX idx_menu_status ON sys_menu_ (status_);
CREATE INDEX idx_menu_parent_id ON sys_menu_ (parent_id_);

COMMENT ON TABLE sys_menu_ IS '菜单权限';
COMMENT ON COLUMN sys_menu_.owner_app_id_ IS '所属应用标识';
COMMENT ON COLUMN sys_menu_.node_key_ IS '节点标识';
COMMENT ON COLUMN sys_menu_.node_path_ IS '节点路径';
COMMENT ON COLUMN sys_menu_.key_ IS '菜单标识，权限标识';
COMMENT ON COLUMN sys_menu_.name_ IS '菜单名称';
COMMENT ON COLUMN sys_menu_.status_ IS '菜单状态';
COMMENT ON COLUMN sys_menu_.desc_ IS '菜单描述';
COMMENT ON COLUMN sys_menu_.default_grant_ IS '默认授予登录用户';
COMMENT ON COLUMN sys_menu_.visible_ IS '显示状态';
COMMENT ON COLUMN sys_menu_.menu_type_ IS '菜单类型';
COMMENT ON COLUMN sys_menu_.path_ IS '访问路径';
COMMENT ON COLUMN sys_menu_.component_ IS '引用组件';
COMMENT ON COLUMN sys_menu_.icon_ IS '菜单图标';
COMMENT ON COLUMN sys_menu_.tag_ IS '标签';
COMMENT ON COLUMN sys_menu_.classes_ IS '类型';
COMMENT ON COLUMN sys_menu_.sort_ IS '排序编号';
COMMENT ON COLUMN sys_menu_.parent_id_ IS '上级权限标识';
COMMENT ON COLUMN sys_menu_.create_by_ IS '创建人姓名';
COMMENT ON COLUMN sys_menu_.create_time_ IS '创建时间';
COMMENT ON COLUMN sys_menu_.update_by_ IS '修改人姓名';
COMMENT ON COLUMN sys_menu_.update_time_ IS '修改时间';
COMMENT ON COLUMN sys_menu_.owner_user_id_ IS '所属用户标识';
COMMENT ON COLUMN sys_menu_.owner_user_account_ IS '所属用户账号';
COMMENT ON COLUMN sys_menu_.owner_dept_id_ IS '所属部门标识';
COMMENT ON COLUMN sys_menu_.owner_dept_code_ IS '所属部门编码';
COMMENT ON COLUMN sys_menu_.owner_dept_name_ IS '所属部门名称';
CREATE TABLE IF NOT EXISTS sys_model_ (
                                          module_ CHAR(25) NOT NULL, -- 绑定功能
    editor_model_json_ TEXT,   -- 模型配置
    PRIMARY KEY (module_)
    );

COMMENT ON TABLE sys_model_ IS '功能扩展模型';
COMMENT ON COLUMN sys_model_.module_ IS '绑定功能';
COMMENT ON COLUMN sys_model_.editor_model_json_ IS '模型配置';
CREATE TABLE IF NOT EXISTS sys_role_ (
                                         id_ CHAR(32) NOT NULL,
    owner_app_id_ CHAR(32), -- 所属应用标识
    key_ VARCHAR(100), -- 角色标识
    name_ VARCHAR(255), -- 角色名称
    status_ CHAR(1), -- 角色状态
    desc_ VARCHAR(2000), -- 角色描述
    default_grant_ CHAR(1), -- 默认授予登录用户
    priority_ INTEGER, -- 优先级（多个角色存在冲突时以哪个角色为主）
    main_page_path_ VARCHAR(255), -- 主页地址
    sort_ INTEGER, -- 排序编号
    create_by_ VARCHAR(128), -- 创建人姓名
    create_time_ TIMESTAMP, -- 创建时间
    update_by_ VARCHAR(128), -- 修改人姓名
    update_time_ TIMESTAMP, -- 修改时间
    owner_user_id_ VARCHAR(128), -- 所属用户标识
    owner_user_account_ VARCHAR(255), -- 所属用户账号
    owner_dept_id_ VARCHAR(128), -- 所属部门标识
    owner_dept_code_ VARCHAR(255), -- 所属部门编码
    owner_dept_name_ VARCHAR(255), -- 所属部门名称
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_role_app_id ON sys_role_ (owner_app_id_);
CREATE INDEX idx_role_key ON sys_role_ (key_);
CREATE INDEX idx_role_status ON sys_role_ (status_);

COMMENT ON TABLE sys_role_ IS '角色管理';
COMMENT ON COLUMN sys_role_.owner_app_id_ IS '所属应用标识';
COMMENT ON COLUMN sys_role_.key_ IS '角色标识';
COMMENT ON COLUMN sys_role_.name_ IS '角色名称';
COMMENT ON COLUMN sys_role_.status_ IS '角色状态';
COMMENT ON COLUMN sys_role_.desc_ IS '角色描述';
COMMENT ON COLUMN sys_role_.default_grant_ IS '默认授予登录用户';
COMMENT ON COLUMN sys_role_.priority_ IS '优先级（多个角色存在冲突时以哪个角色为主）';
COMMENT ON COLUMN sys_role_.main_page_path_ IS '主页地址';
COMMENT ON COLUMN sys_role_.sort_ IS '排序编号';
COMMENT ON COLUMN sys_role_.create_by_ IS '创建人姓名';
COMMENT ON COLUMN sys_role_.create_time_ IS '创建时间';
COMMENT ON COLUMN sys_role_.update_by_ IS '修改人姓名';
COMMENT ON COLUMN sys_role_.update_time_ IS '修改时间';
COMMENT ON COLUMN sys_role_.owner_user_id_ IS '所属用户标识';
COMMENT ON COLUMN sys_role_.owner_user_account_ IS '所属用户账号';
COMMENT ON COLUMN sys_role_.owner_dept_id_ IS '所属部门标识';
COMMENT ON COLUMN sys_role_.owner_dept_code_ IS '所属部门编码';
COMMENT ON COLUMN sys_role_.owner_dept_name_ IS '所属部门名称';
CREATE TABLE IF NOT EXISTS sys_role_ex_ (
                                            id_ BIGINT NOT NULL,
                                            role_id_ BIGINT, -- 角色标识
                                            ex_role_id_ BIGINT, -- 相斥角色标识
                                            PRIMARY KEY (id_)
    );

COMMENT ON TABLE sys_role_ex_ IS '相斥角色';
COMMENT ON COLUMN sys_role_ex_.role_id_ IS '角色标识';
COMMENT ON COLUMN sys_role_ex_.ex_role_id_ IS '相斥角色标识';
CREATE TABLE IF NOT EXISTS sys_role_menu_ (
                                              id_ CHAR(32) NOT NULL,
    role_id_ CHAR(32), -- 角色标识
    app_id_ CHAR(32), -- 应用标识
    menu_id_ CHAR(32), -- 菜单标识
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_role_menu_role_id ON sys_role_menu_ (role_id_);
CREATE INDEX idx_role_menu_app_id ON sys_role_menu_ (app_id_);
CREATE INDEX idx_role_menu_menu_id ON sys_role_menu_ (menu_id_);

COMMENT ON TABLE sys_role_menu_ IS '角色菜单关联';
COMMENT ON COLUMN sys_role_menu_.role_id_ IS '角色标识';
COMMENT ON COLUMN sys_role_menu_.app_id_ IS '应用标识';
COMMENT ON COLUMN sys_role_menu_.menu_id_ IS '菜单标识';
CREATE TABLE IF NOT EXISTS sys_user_ (
                                         id_ CHAR(128) NOT NULL,
    dept_id_ CHAR(128), -- 所属部门
    dept_identity_ VARCHAR(100), -- 部门内身份
    username_ VARCHAR(255), -- 账号
    password_ VARCHAR(1000), -- 密码
    mask_v_ VARCHAR(1000),
    password_policy_ CHAR(4), -- 密码策略
    password_expired_ DATE, -- 现有密码过期时间
    old_password_ VARCHAR(1000), -- 旧密码
    nickname_ VARCHAR(255), -- 昵称
    realname_ VARCHAR(255), -- 真实姓名
    sex_ CHAR(1), -- 性别
    card_type_ CHAR(1), -- 证件类型
    card_no_ VARCHAR(255), -- 证件号码
    phone_ VARCHAR(50), -- 手机号码
    email_ VARCHAR(255), -- 邮箱
    work_addr_ VARCHAR(2000), -- 工作地点
    work_unit_ VARCHAR(255), -- 工作单位
    work_pos_ VARCHAR(255), -- 工作职务
    work_rank_ VARCHAR(255), -- 工作职级
    home_addr_ VARCHAR(2000), -- 家庭住址
    home_phone_ VARCHAR(255), -- 家庭电话
    source_ CHAR(1), -- 用户来源
    sort_ INTEGER, -- 排序编号
    status_ CHAR(1), -- 用户状态
    account_lock_ CHAR(1), -- 账户是否锁定
    last_change_password_ TIMESTAMP, -- 上一次修改密码时间
    create_by_ VARCHAR(128), -- 创建人姓名
    create_time_ TIMESTAMP, -- 创建时间
    update_by_ VARCHAR(128), -- 修改人姓名
    update_time_ TIMESTAMP, -- 修改时间
    owner_user_id_ VARCHAR(128), -- 所属用户标识
    owner_user_account_ VARCHAR(255), -- 所属用户账号
    owner_dept_id_ VARCHAR(128), -- 所属部门标识
    owner_dept_code_ VARCHAR(255), -- 所属部门编码
    owner_dept_name_ VARCHAR(255), -- 所属部门名称
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_user_dept_id ON sys_user_ (dept_id_);
CREATE INDEX idx_user_username ON sys_user_ (username_);
CREATE INDEX idx_user_status ON sys_user_ (status_);
CREATE INDEX idx_user_account_lock ON sys_user_ (account_lock_);
CREATE INDEX idx_user_phone ON sys_user_ (phone_);
CREATE INDEX idx_user_email ON sys_user_ (email_);

COMMENT ON TABLE sys_user_ IS '用户';
COMMENT ON COLUMN sys_user_.dept_id_ IS '所属部门';
COMMENT ON COLUMN sys_user_.dept_identity_ IS '部门内身份';
COMMENT ON COLUMN sys_user_.username_ IS '账号';
COMMENT ON COLUMN sys_user_.password_ IS '密码';
COMMENT ON COLUMN sys_user_.password_policy_ IS '密码策略';
COMMENT ON COLUMN sys_user_.password_expired_ IS '现有密码过期时间';
COMMENT ON COLUMN sys_user_.old_password_ IS '旧密码';
COMMENT ON COLUMN sys_user_.nickname_ IS '昵称';
COMMENT ON COLUMN sys_user_.realname_ IS '真实姓名';
COMMENT ON COLUMN sys_user_.sex_ IS '性别';
COMMENT ON COLUMN sys_user_.card_type_ IS '证件类型';
COMMENT ON COLUMN sys_user_.card_no_ IS '证件号码';
COMMENT ON COLUMN sys_user_.phone_ IS '手机号码';
COMMENT ON COLUMN sys_user_.email_ IS '邮箱';
COMMENT ON COLUMN sys_user_.work_addr_ IS '工作地点';
COMMENT ON COLUMN sys_user_.work_unit_ IS '工作单位';
COMMENT ON COLUMN sys_user_.work_pos_ IS '工作职务';
COMMENT ON COLUMN sys_user_.work_rank_ IS '工作职级';
COMMENT ON COLUMN sys_user_.home_addr_ IS '家庭住址';
COMMENT ON COLUMN sys_user_.home_phone_ IS '家庭电话';
COMMENT ON COLUMN sys_user_.source_ IS '用户来源';
COMMENT ON COLUMN sys_user_.sort_ IS '排序编号';
COMMENT ON COLUMN sys_user_.status_ IS '用户状态';
COMMENT ON COLUMN sys_user_.account_lock_ IS '账户是否锁定';
COMMENT ON COLUMN sys_user_.last_change_password_ IS '上一次修改密码时间（这里配置了一段时间仍然可以使用旧密码来登录）';
COMMENT ON COLUMN sys_user_.create_by_ IS '创建人姓名';
COMMENT ON COLUMN sys_user_.create_time_ IS '创建时间';
COMMENT ON COLUMN sys_user_.update_by_ IS '修改人姓名';
COMMENT ON COLUMN sys_user_.update_time_ IS '修改时间';
COMMENT ON COLUMN sys_user_.owner_user_id_ IS '所属用户标识';
COMMENT ON COLUMN sys_user_.owner_user_account_ IS '所属用户账号';
COMMENT ON COLUMN sys_user_.owner_dept_id_ IS '所属部门标识';
COMMENT ON COLUMN sys_user_.owner_dept_code_ IS '所属部门编码';
COMMENT ON COLUMN sys_user_.owner_dept_name_ IS '所属部门名称';
CREATE TABLE IF NOT EXISTS sys_user_ext_attr_ (
                                                  id_ CHAR(128) NOT NULL, -- 用户标识，与用户一对一
    attribution_ JSONB, -- 扩展属性
    PRIMARY KEY (id_)
    );

COMMENT ON COLUMN sys_user_ext_attr_.id_ IS '用户标识，与用户一对一';
COMMENT ON COLUMN sys_user_ext_attr_.attribution_ IS '扩展属性';
CREATE TABLE IF NOT EXISTS sys_user_manager_dept_ (
                                                      id_ CHAR(32) NOT NULL, -- 标识
    user_id_ CHAR(128), -- 用户标识
    dept_id_ CHAR(128), -- 部门标识
    data_access_scope_ VARCHAR(20), -- 数据可见范围
    contain_sub_ CHAR(1), -- 是否包含子部门
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_user_manager_user_id ON sys_user_manager_dept_ (user_id_);
CREATE INDEX idx_user_manager_dept_id ON sys_user_manager_dept_ (dept_id_);

COMMENT ON TABLE sys_user_manager_dept_ IS '用户管辖组织机构';
COMMENT ON COLUMN sys_user_manager_dept_.id_ IS '标识';
COMMENT ON COLUMN sys_user_manager_dept_.user_id_ IS '用户标识';
COMMENT ON COLUMN sys_user_manager_dept_.dept_id_ IS '部门标识';
COMMENT ON COLUMN sys_user_manager_dept_.data_access_scope_ IS '数据可见范围';
COMMENT ON COLUMN sys_user_manager_dept_.contain_sub_ IS '是否包含子部门';
CREATE TABLE IF NOT EXISTS sys_user_role_ (
                                              id_ CHAR(32) NOT NULL,
    user_id_ CHAR(128),
    role_id_ CHAR(32),
    app_id_ CHAR(32),
    PRIMARY KEY (id_)
    );

CREATE INDEX idx_user_role_user_id ON sys_user_role_ (user_id_);
CREATE INDEX idx_user_role_role_id ON sys_user_role_ (role_id_);
CREATE INDEX idx_user_role_app_id ON sys_user_role_ (app_id_);
