package tech.hljzj.infrastructure.config.activiti;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tech.hljzj.activiti.manager.SimpleProcessRegister;
import tech.hljzj.activiti.pojo.ActionTrigger;
import tech.hljzj.activiti.pojo.AssignExecutor;
import tech.hljzj.activiti.pojo.User;
import tech.hljzj.activiti.pojo.node.ProcessProp;
import tech.hljzj.activiti.register.NameProcessor;
import tech.hljzj.framework.security.bean.LoginUser;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.framework.util.web.AuthUtil;
import tech.hljzj.infrastructure.domain.SysUser;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class UserCreateProcess extends SimpleProcessRegister implements NameProcessor, ApplicationRunner {
    @Autowired
    private RuntimeService runtimeService;

    @Override
    public String processCode() {
        return "user_create";
    }

    @Override
    public String processName() {
        return "新建用户";
    }

    @Override
    public List<ProcessProp> props() {
        // 这里期望的是放入用户的全部属性
        List<ProcessProp> props = new ArrayList<>();
        props.add(ProcessProp.create("id").setReadonly(true).setVisible(false).setName("id").setComponent("ElInput"));
        props.add(ProcessProp.create("username").setReadonly(true).setName("账号").setComponent("ElInput"));
        props.add(ProcessProp.create("nickname").setReadonly(true).setName("昵称").setComponent("ElInput"));
        props.add(ProcessProp.create("realname").setReadonly(true).setName("真实姓名").setComponent("ElInput"));
        props.add(ProcessProp.create("sex").setReadonly(true).setName("性别").setComponent("ElInput"));
        props.add(ProcessProp.create("cardType").setReadonly(true).setName("证件类型").setComponent("ElInput"));
        props.add(ProcessProp.create("cardNo").setReadonly(true).setName("证件号码").setComponent("ElInput"));
        props.add(ProcessProp.create("phone").setReadonly(true).setName("手机号码").setComponent("ElInput"));
        props.add(ProcessProp.create("email").setReadonly(true).setName("邮箱").setComponent("ElInput"));
        props.add(ProcessProp.create("workAddr").setReadonly(true).setName("工作地点").setComponent("ElInput"));
        props.add(ProcessProp.create("workUnit").setReadonly(true).setName("工作单位").setComponent("ElInput"));
        props.add(ProcessProp.create("workPos").setReadonly(true).setName("工作职务").setComponent("ElInput"));
        props.add(ProcessProp.create("workRank").setReadonly(true).setName("工作职级").setComponent("ElInput"));
        props.add(ProcessProp.create("homeAddr").setReadonly(true).setName("家庭住址").setComponent("ElInput"));
        props.add(ProcessProp.create("homePhone").setReadonly(true).setName("家庭电话").setComponent("ElInput"));
        props.add(ProcessProp.create("source").setReadonly(true).setName("用户来源").setComponent("ElInput"));
        props.add(ProcessProp.create("sort").setReadonly(true).setName("排序编号").setComponent("ElInput"));
        props.add(ProcessProp.create("status").setReadonly(false).setCanUpdate(true).setName("用户状态").setComponent("ElInput"));
        props.add(ProcessProp.create("accountLock").setReadonly(false).setCanUpdate(false).setName("账户是否锁定").setComponent("ElInput"));
        return props;
    }

    @Override
    public List<AssignExecutor> assignMethod() {
        return Collections.emptyList();
    }

    @Override
    public List<ActionTrigger> actionTriggerMethod() {
        return Collections.emptyList();
    }

    @Override
    public List<User> assignExecutor(String processId, String assignTag, User processInitiator, List<String> roles, Map<String, Object> variables) {
        return Collections.emptyList();
    }

    @Override
    public void updateProp(String processId, String propKey, String newValue, User processInitiator) {
    }

    @Override
    public void triggerMethod(String processId, List<String> actions, User processInitiator) {

    }


    @Override
    public String generateProcessName(Object parameter) {
        UserInfo user = AuthUtil.getLoginUserDetail();
        return user.getName() + "的创建用户[" + ((SysUser) parameter).getRealname() + "]申请";
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(registry);
        for (Deployment model : repositoryService.createDeploymentQuery().list()) {
            repositoryService.deleteDeployment(model.getId(), true);
        }
    }
}
