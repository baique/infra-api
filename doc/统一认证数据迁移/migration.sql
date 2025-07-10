use infra;
-- =============================================
-- 数据库迁移脚本
-- 从 tyrzxt 迁移到 infra
-- 脚本支持多次执行，如遇到失败可修正后反复执行
-- =============================================

-- 设置SQL模式和字符集
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- =============================================
-- 1. 部门表迁移 (department -> sys_dept_)
-- =============================================
START TRANSACTION;

-- 只删除需要更新的数据
DELETE t FROM sys_dept_ t
                  INNER JOIN tyrzxt.department s ON t.id_ = s.Department_ID;

-- 插入数据
INSERT INTO sys_dept_ (
    id_,
    parent_id_,
    key_,
    name_,
    alias_,
    short_name_,
    type_,
    status_,
    enable_,
    sort_,
    tmp_,
    create_by_,
    create_time_,
    update_by_,
    update_time_
)
SELECT
    Department_ID,                    -- id_
    Department_Parent,                -- parent_id_
    Department_Code,                  -- key_
    Department_Name,                  -- name_
    dept_short_name,                  -- alias_
    dept_short_name,                  -- short_name_
    dept_type,                        -- type_
    CASE
        WHEN invalid_flag = '1' THEN '0'  -- 无效
        ELSE '1'                          -- 有效
        END,                              -- status_
    CASE
        WHEN invalid_flag = '1' THEN '0'  -- 禁用
        ELSE '1'                          -- 启用
        END,                              -- enable_
    sort_no,                          -- sort_
    istmp,         -- 是否临时部门
    Create_USER,                      -- create_by_
    Create_TIME,                      -- create_time_
    MODIFY_USER,                      -- update_by_
    MODIFY_TIME                       -- update_time_
FROM tyrzxt.department;

COMMIT;

-- =============================================
-- 2. 应用表迁移 (app_info -> sys_app_)
-- =============================================
START TRANSACTION;

-- 只删除需要更新的数据
DELETE t FROM sys_app_ t
                  INNER JOIN tyrzxt.app_info s ON t.id_ = s.App_ID;

-- 插入数据
INSERT INTO sys_app_ (
    id_,
    key_,
    name_,
    secret_,
    status_,
    desc_,
    sort_,
    create_by_,
    create_time_,
    update_by_,
    update_time_,
    owner_dept_id_
)
SELECT
    App_ID,                           -- id_
    App_Name,                         -- key_
    App_Name,                         -- name_
    App_secret,                       -- secret_
    CASE
        WHEN App_State = 1 THEN '1'   -- 启用
        ELSE '0'                      -- 禁用
        END,                              -- status_
    NULL,                             -- desc_ (源表无对应字段)
    App_scope,                        -- sort_
    Create_User,                      -- create_by_
    Create_Time,                      -- create_time_
    Update_User,                      -- update_by_
    Update_Time,                      -- update_time_
    App_Department_ID                 -- owner_dept_id_
FROM tyrzxt.app_info;

COMMIT;

-- =============================================
-- 3. 权限表迁移 (permission_info -> sys_menu_)
-- =============================================
START TRANSACTION;

-- 只删除需要更新的数据
DELETE t FROM sys_menu_ t
                  INNER JOIN tyrzxt.permission_info s ON t.id_ = s.Permission_ID;

-- 插入数据
INSERT INTO sys_menu_ (
    id_,
    owner_app_id_,
    key_,
    name_,
    status_,
    visible_,
    desc_,
    tag_,
    classes_,
    sort_,
    parent_id_,
    create_by_,
    create_time_,
    update_by_,
    update_time_
)
SELECT
    Permission_ID,                    -- id_
    App_ID,                          -- owner_app_id_
    Permission_Value,                 -- key_
    Permission_Name,                  -- name_
    '1',                             -- status_ (默认启用)
    '1',                             -- visible_（默认显示）
    Permission_DESC,                  -- desc_
    Permission_Tag,                   -- tag_
    Permission_Class,                 -- classes_
    Permission_Sort,                  -- sort_
    Permission_Parent,                -- parent_id_
    Create_User,                      -- create_by_
    Create_Time,                      -- create_time_
    Update_User,                      -- update_by_
    Update_Time                       -- update_time_
FROM tyrzxt.permission_info;

COMMIT;

-- =============================================
-- 4. 角色表迁移 (role_info -> sys_role_)
-- =============================================
START TRANSACTION;

-- 只删除需要更新的数据
DELETE t FROM sys_role_ t
                  INNER JOIN tyrzxt.role_info s ON t.id_ = s.Role_ID;

-- 插入数据
INSERT INTO sys_role_ (
    id_,
    owner_app_id_,
    key_,
    name_,
    status_,
    desc_,
    sort_,
    create_by_,
    create_time_,
    update_by_,
    update_time_
)
SELECT
    Role_ID,                          -- id_
    App_ID,                          -- owner_app_id_
    Role_Value,                       -- key_
    Role_Name,                        -- name_
    '1',                             -- status_ (默认启用)
    Role_DESC,                        -- desc_
    0,                               -- sort_ (默认0)
    Create_User,                      -- create_by_
    COALESCE(Create_Time, NOW()),    -- create_time_ (默认当前时间)
    Update_User,                      -- update_by_
    Update_Time                       -- update_time_
FROM tyrzxt.role_info;

COMMIT;

-- =============================================
-- 5. 角色权限关联表迁移 (role_permission -> sys_role_menu_)
-- =============================================
START TRANSACTION;

-- 只删除需要更新的数据
DELETE t FROM sys_role_menu_ t
                  INNER JOIN tyrzxt.role_permission s ON t.id_ = s.Role_Permission_ID;

-- 插入数据
INSERT INTO sys_role_menu_ (
    id_,
    role_id_,
    app_id_,
    menu_id_
)
SELECT
    rp.Role_Permission_ID,            -- id_
    rp.Role_ID,                       -- role_id_
    r.App_ID,                         -- app_id_ (通过联表查询获取)
    rp.Permission_ID                  -- menu_id_
FROM tyrzxt.role_permission rp
         INNER JOIN tyrzxt.role_info r ON rp.Role_ID = r.Role_ID;

COMMIT;

-- =============================================
-- 6. 用户表迁移 (user_info -> sys_user_)
-- =============================================
START TRANSACTION;

-- 只删除需要更新的数据
DELETE t FROM sys_user_ t
                  INNER JOIN tyrzxt.user_info s ON t.id_ = s.USER_ID;

-- 插入数据
INSERT INTO sys_user_ (
    id_,
    dept_id_,
    username_,
    old_password_,
    nickname_,
    realname_,
    phone_,
    email_,
    work_pos_,
    home_phone_,
    home_addr_,
    sort_,
    status_,
    account_lock_,
    create_by_,
    create_time_,
    update_by_,
    update_time_
)
SELECT
    USER_ID,                          -- id_
    USER_Department,                  -- dept_id_
    USER_Account,                     -- username_
    concat('tyrz:',User_Password),                    -- old_password_
    USER_nickname,                    -- nickname_
    USER_FULL_NAME,                   -- realname_
    USER_PHone,                       -- phone_
    USER_Email,                       -- email_
    USER_Position,                    -- work_pos_
    USER_HomePhone,                   -- home_phone_
    USER_Address_Detailed,            -- home_addr_
    COALESCE(USER_Sort_no, 0),        -- sort_ (默认0)
    COALESCE(
            CASE
                WHEN USER_state = 1 THEN '1'  -- 启用
                ELSE '0'                      -- 禁用
                END, '1'                         -- 默认启用
    ),                                -- status_
    CASE
        WHEN account_lock_time IS NOT NULL THEN '1'  -- 锁定
        ELSE '0'                                     -- 未锁定
        END,                              -- account_lock_
    Create_User,                      -- create_by_
    COALESCE(USER_createTime, NOW()), -- create_time_ (默认当前时间)
    NULL,                            -- update_by_ (源表无对应字段)
    NULL                             -- update_time_ (源表无对应字段)
FROM tyrzxt.user_info;

update sys_user_ a
    inner join tyrzxt.user_info  b on a.id_ = b.user_id
set a.work_unit_ = b.USER_Corporation
where (a.work_unit_ is null or a.work_unit_ = '') and (b.USER_Corporation is not null and b.USER_Corporation <> '');

COMMIT;

-- =============================================
-- 7. 用户角色关联表迁移 (user_role -> sys_user_role_)
-- =============================================
START TRANSACTION;

-- 只删除需要更新的数据
DELETE t FROM sys_user_role_ t
                  INNER JOIN tyrzxt.user_role s ON t.id_ = s.USER_ROLE_ID;

-- 插入数据
INSERT INTO sys_user_role_ (
    id_,
    user_id_,
    role_id_,
    app_id_
)
SELECT
    ur.USER_ROLE_ID,                  -- id_
    ur.User_ID,                       -- user_id_
    ur.Role_ID,                       -- role_id_
    r.App_ID                          -- app_id_ (通过联表查询获取)
FROM tyrzxt.user_role ur
         INNER JOIN tyrzxt.role_info r ON ur.Role_ID = r.Role_ID;

COMMIT;

-- =============================================
-- 更新parent_id_为空或null的记录
-- =============================================
START TRANSACTION;

-- 更新菜单表
UPDATE sys_menu_
SET parent_id_ = '0'
WHERE parent_id_ IS NULL OR parent_id_ = '';
UPDATE sys_menu_
SET create_time_ = NOW()
WHERE create_time_ IS NULL;
UPDATE sys_menu_
SET update_time_ = NOW()
WHERE update_time_ IS NULL;
COMMIT;

-- =============================================
-- 批量更新默认值
-- =============================================
START TRANSACTION;

-- 更新角色表默认值
-- 状态为空时默认启用
UPDATE sys_role_
SET status_ = '1'
WHERE status_ IS NULL OR status_ = '';

-- 排序为空时默认0
UPDATE sys_role_
SET sort_ = 0
WHERE sort_ IS NULL OR sort_ = '';

-- 创建时间为空时默认当前时间
UPDATE sys_role_
SET create_time_ = NOW()
WHERE create_time_ IS NULL;
UPDATE sys_role_
SET update_time_ = NOW()
WHERE update_time_ IS NULL;
UPDATE sys_role_
SET default_grant_ = 0
WHERE default_grant_ IS NULL OR default_grant_ = '';


-- 更新用户表默认值
-- 状态为空时默认启用
UPDATE sys_user_
SET status_ = '1'
WHERE status_ IS NULL OR status_ = '';

-- 排序为空时默认0
UPDATE sys_user_
SET sort_ = 0
WHERE sort_ IS NULL OR sort_ = '';

-- 创建时间为空时默认当前时间
UPDATE sys_user_
SET create_time_ = NOW()
WHERE create_time_ IS NULL;
UPDATE sys_user_
SET update_time_ = NOW()
WHERE update_time_ IS NULL;
-- 更新权限表默认值
-- 状态为空时默认启用
UPDATE sys_menu_
SET status_ = '1'
WHERE status_ IS NULL OR status_ = '';

-- 排序为空时默认0
UPDATE sys_menu_
SET sort_ = 0
WHERE sort_ IS NULL OR sort_ = '';

UPDATE sys_menu_
SET default_grant_ = 0
WHERE default_grant_ IS NULL OR default_grant_ = '';

-- 创建时间为空时默认当前时间
UPDATE sys_menu_
SET create_time_ = NOW()
WHERE create_time_ IS NULL;

-- 更新应用表默认值
-- 状态为空时默认启用
UPDATE sys_app_
SET status_ = '1'
WHERE status_ IS NULL OR status_ = '';

-- 排序为空时默认0
UPDATE sys_app_
SET sort_ = 0
WHERE sort_ IS NULL OR sort_ = '';

-- 创建时间为空时默认当前时间
UPDATE sys_app_
SET create_time_ = NOW()
WHERE create_time_ IS NULL;
UPDATE sys_app_
SET update_time_ = NOW()
WHERE update_time_ IS NULL;


-- 更新部门表
UPDATE sys_dept_
SET parent_id_ = '0'
WHERE parent_id_ IS NULL OR parent_id_ = '';

-- 更新部门表默认值
-- 状态为空时默认启用
UPDATE sys_dept_
SET status_ = '1'
WHERE status_ IS NULL OR status_ = '';

-- 是否启用为空时默认启用
UPDATE sys_dept_
SET enable_ = '1'
WHERE enable_ IS NULL OR enable_ = '';

-- 排序为空时默认0
UPDATE sys_dept_
SET sort_ = 0
WHERE sort_ IS NULL OR sort_ = '';

-- 创建时间为空时默认当前时间
UPDATE sys_dept_
SET create_time_ = NOW()
WHERE create_time_ IS NULL;

UPDATE sys_dept_
SET update_time_ = NOW()
WHERE update_time_ IS NULL;

COMMIT;

-- 恢复外键检查
SET FOREIGN_KEY_CHECKS = 1;

-- =============================================
-- 更新部门表的node_key_和node_path_字段
-- =============================================
WITH RECURSIVE dept_cte AS (
    -- 顶级部门
    SELECT
        id_,
        parent_id_,
        SUBSTRING(md5(d.id_), 1, 16) AS node_key,
        cast(concat('/',SUBSTRING(md5(d.id_), 1, 16)) as char(2000)) AS node_path
    FROM sys_dept_ d
    WHERE parent_id_ = '0'

    UNION ALL

    -- 子部门
    SELECT
        d.id_,
        d.parent_id_,
        SUBSTRING(md5(d.id_), 1, 16) AS node_key,
        CONCAT(dc.node_path,'/', SUBSTRING(md5(d.id_), 1, 16)) AS node_path
    FROM sys_dept_ d
             JOIN dept_cte dc ON d.parent_id_ = dc.id_
)
UPDATE sys_dept_ s
    JOIN dept_cte c ON s.id_ = c.id_
    SET
        s.node_key_ = c.node_key,
        s.node_path_ = c.node_path;


-- =============================================
-- 更新菜单表的node_key_和node_path_字段
-- =============================================
WITH RECURSIVE menu_cte AS (
    -- 顶级部门
    SELECT
        id_,
        parent_id_,
        SUBSTRING(md5(d.id_), 1, 16) AS node_key,
        cast(concat('/',SUBSTRING(md5(d.id_), 1, 16)) as char(2000)) AS node_path
    FROM sys_menu_ d
    WHERE parent_id_ = '0'
    UNION ALL
    -- 子部门
    SELECT
        d.id_,
        d.parent_id_,
        SUBSTRING(md5(d.id_), 1, 16) AS node_key,
        CONCAT(dc.node_path,'/', SUBSTRING(md5(d.id_), 1, 16)) AS node_path
    FROM sys_menu_ d
             JOIN menu_cte dc ON d.parent_id_ = dc.id_
)
UPDATE sys_menu_ s
    JOIN menu_cte c ON s.id_ = c.id_
    SET
        s.node_key_ = c.node_key,
        s.node_path_ = c.node_path;
-- 合并专案组成员信息
insert ignore into sys_dept_external_user_ (id_,dept_id_,user_id_) SELECT id,department_id,user_id FROM `tyrzxt`.`department_contain_user`;
-- 合并所有管辖部门
insert ignore into sys_user_manager_dept_ (id_,user_id_,dept_id_,data_access_scope_,contain_sub_) select id,user_id,dept_id,if(manager_scope = '0','self','all'),manager_children from tyrzxt.sys_manager_dept
