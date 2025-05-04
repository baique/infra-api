package tech.hljzj.activiti.util;

import cn.hutool.core.util.StrUtil;
import org.activiti.engine.delegate.DelegateExecution;
import tech.hljzj.activiti.manager.ActException;
import tech.hljzj.activiti.pojo.User;
import tech.hljzj.activiti.pojo.UserSelector;
import tech.hljzj.activiti.pojo.enums.ApprovalMultiEnum;
import tech.hljzj.activiti.pojo.enums.AssigneeTypeEnum;
import tech.hljzj.activiti.pojo.node.ApprovalNode;
import tech.hljzj.activiti.pojo.node.Node;
import tech.hljzj.activiti.register.IProcess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static tech.hljzj.activiti.manager.SimpleProcessRegister.DYNAMIC_NODE_MODE;

public class InjectUtil {


    public static User getProcessInitiatorUser(Map<String, Object> variables) {
        return getProcessUser(variables, "_user_initiator::");
    }

    public static User getProcessUser(Map<String, Object> variables, String startWithKey) {
        String idKey = startWithKey + "UserId";
        String userNameKey = startWithKey + "UserName";
        String userRoleKey = startWithKey + "UserRole";
        String userRoleNameKey = startWithKey + "UserRoleName";
        User user = new User();
        if (variables.get(idKey) != null) {
            user.setId(variables.get(idKey).toString());
        }
        if (variables.get(userNameKey) != null) {
            user.setName(variables.get(userNameKey).toString());
        }
        if (variables.get(userRoleKey) != null) {
            user.setRoleId(variables.get(userRoleKey).toString());
        }
        if (variables.get(userRoleNameKey) != null) {
            user.setRoleName(variables.get(userRoleNameKey).toString());
        }
        return user;
    }

    public static void setProcessInitiatorUser(Map<String, Object> variables, User user) {
        setProcessUser(variables, "_user_initiator::", user);
    }

    public static void setProcessUser(Map<String, Object> variable, String startWithKey, User user) {
        String idKey = startWithKey + "UserId";
        String userNameKey = startWithKey + "UserName";
        String userRoleKey = startWithKey + "UserRole";
        String userRoleNameKey = startWithKey + "UserRoleName";
        String deptKey = startWithKey + "Dept";
        String deptName = startWithKey + "DeptName";
        variable.put(idKey, user.getId());
        variable.put(userNameKey, user.getName());
        variable.put(userRoleKey, user.getRoleId());
        variable.put(userRoleNameKey, user.getRoleName());
    }


    public static void injectDynamicExecutor(String processId, Node taskDefine, DelegateExecution execution, IProcess register) {
        if (!(taskDefine instanceof ApprovalNode)) {
            return;
        }

        ApprovalNode aNode = (ApprovalNode) taskDefine;
        String assignTag = aNode.getCustomFlag();
        if (ApprovalMultiEnum.JOINT == aNode.getMulti()) {
            //在这里获取一下通过的百分比
            String multiPercent = aNode.getMultiPercent();
            execution.setTransientVariable(DYNAMIC_NODE_MODE + aNode.getId(), StrUtil.blankToDefault(multiPercent, "100%"));
        }

        List<String> assign = new ArrayList<>();
        switch (aNode.getAssigneeType()) {
            case ROLE:
                //指定角色
                assign = (new ArrayList<>(aNode.getRoles()));
                break;
            case USER:
                //指定用户
                assign = new ArrayList<>(aNode.getUsers());
                break;
        }

        Map<String, Object> variables = execution.getVariables();

        String variableKey = taskDefine.getId() + "Collection";

        if (StrUtil.isNotBlank(assignTag) && aNode.getAssigneeType() == AssigneeTypeEnum.ROLE) {
            List<User> users = register.assignExecutor(processId, assignTag, getProcessInitiatorUser(variables), assign, variables);
            if (users != null) {
                execution.setTransientVariable(
                    variableKey,
                    users.stream().map(UserSelector::getId).collect(Collectors.toList())
                );
            }
        } else {
            execution.setTransientVariable(
                variableKey,
                assign
            );
        }
        //可以是从瞬态变量中获取
        Object var = execution.getVariable(variableKey);
        if (!(var instanceof Collection<?>)) {
            throw new ActException("执行人信息只能是ID列表");
        }
        Collection<?> us = (Collection<?>) var;
        if (us.size() == 0) {
            throw new ActException("任务未分配到任何人员!");
        }
    }

}
