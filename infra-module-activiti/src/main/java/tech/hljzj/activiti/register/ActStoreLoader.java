package tech.hljzj.activiti.register;


import tech.hljzj.activiti.pojo.Role;
import tech.hljzj.activiti.pojo.User;

import java.io.Serializable;
import java.util.List;

/**
 * 用户载入工具
 *
 * @author wa
 */
public interface ActStoreLoader {
    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    List<User> users(List<Serializable> ids);

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    List<Role> roles(List<Serializable> ids);
}