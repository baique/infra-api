package tech.hljzj.infrastructure.controller;

import cn.hutool.core.util.StrUtil;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.hljzj.activiti.config.Registry;
import tech.hljzj.activiti.pojo.User;
import tech.hljzj.activiti.pojo.instance.TaskInfo;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.pojo.form.PageDomain;
import tech.hljzj.framework.security.an.Anonymous;
import tech.hljzj.framework.security.bean.LoginUser;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.framework.util.web.AuthUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 流程管理 sys_activiti_
 * 控制层
 *
 * @author wa
 */
@RestController
@RequestMapping("/infrastructure/sys_activiti")
@Anonymous
public class SysActivitiController extends BaseController {
    public static final String MODULE_NAME = "流程管理";
    private final RepositoryService service;
    private final TaskService taskService;
    private final RuntimeService runtimeService;
    private final Registry registry;

    @Autowired
    public SysActivitiController(RepositoryService service, Registry registry, TaskService taskService, RuntimeService runtimeService) {
        this.service = service;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.registry = registry;
    }


    /**
     * 查询数据
     *
     * @return 查询结果
     */
    @PostMapping("/list")
    @PreAuthorize("auth('sys:activiti:list')")
    public R<List<Map<String, Object>>> page(String key, String name) {
        ModelQuery query = this.service.createModelQuery();
        if (StrUtil.isNotBlank(key)) {
            query.modelKey(key);
        }
        if (StrUtil.isNotBlank(name)) {
            query.modelNameLike(name);
        }
        List<Model> d = query.list();

        return R.ok(d.stream().map(f -> {
            Map<String, Object> mp = new HashMap<>();
            mp.put("id", f.getId());
            mp.put("key", f.getKey());
            mp.put("name", f.getName());
            mp.put("lastUpdateTime", f.getLastUpdateTime());
            return mp;
        }).collect(Collectors.toList()));
    }

    @PostMapping("instances")
//    @PreAuthorize("auth('sys:activiti:list')")
    public R<List<Map<String, Object>>> instances(String key, boolean onlyMyJob, PageDomain page) {
        return R.ok(runtimeService.createProcessInstanceQuery()
            .active()
            .list().stream().map(f -> {
                Map<String, Object> mp = new HashMap<>();
                mp.put("id", f.getId());
                mp.put("bid", f.getBusinessKey());
                mp.put("name", f.getName());
                mp.put("processKey", f.getProcessDefinitionKey());
                mp.put("desc", f.getDescription());
                mp.put("startTime", f.getStartTime());
                return mp;
            }).collect(Collectors.toList())
        );
    }

    /**
     * 查询数据
     *
     * @return 查询结果
     */
    @PostMapping("/jobs")
//    @PreAuthorize("auth('sys:activiti:list')")
    public R<List<TaskInfo>> jobs(String key, boolean onlyMyJob) {
        // 各个审批流程的任务获取
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (onlyMyJob) {
            LoginUser user = AuthUtil.getLoginUser();
            UserInfo uu = user.getUserInfo();
            List<String> ids = new ArrayList<>();
            ids.add(uu.getId());
            taskQuery = taskQuery.taskAssigneeIds(ids)
                .taskCandidateGroupIn(new ArrayList<>(uu.getRole()));
        }
        if (StrUtil.isNotBlank(key)) {
            taskQuery = taskQuery.processDefinitionKey(key);
        }

        return R.ok(taskQuery.orderByTaskCreateTime().desc().list().stream().map(f -> {
            TaskInfo ti = new TaskInfo();
            ti.setId(f.getId());
            ti.setKey(f.getTaskDefinitionKey());
            ti.setName(f.getName());
            ti.setDesc(f.getDescription());
            ti.setAssign(f.getAssignee());
            ti.setProcessId(f.getProcessInstanceId());
            ti.setInstance(f);
            return ti;
        }).collect(Collectors.toList()));
    }

    @PostMapping("/start")
    public R<?> start(String key, String id, String name, User user, @RequestBody(required = false) Map<String, Object> variables) {
        return R.ok(registry.get(key).startProcess(
            id,
            name,
            user,
            variables
        ).getProcessInstanceId());
    }

    @PostMapping("/pass")
    public R<?> pass(String key, String id, User user, @RequestBody(required = false) Map<String, Object> variables) {
        registry.get(key).passTask(
            id,
            user,
            variables
        );
        return R.ok();
    }


    @PostMapping("/deny")
    public R<?> deny(String key, String id, User user, @RequestBody(required = false) Map<String, Object> variables) {
        registry.get(key).denyTask(
            id,
            user,
            variables
        );
        return R.ok();
    }


}