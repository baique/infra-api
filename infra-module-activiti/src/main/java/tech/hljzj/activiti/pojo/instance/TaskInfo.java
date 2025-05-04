package tech.hljzj.activiti.pojo.instance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.activiti.engine.task.Task;
import tech.hljzj.activiti.pojo.FormInfo;
import tech.hljzj.activiti.pojo.TaskProgress;
import tech.hljzj.activiti.pojo.User;

import java.util.List;

@Getter
@Setter
public class TaskInfo {
    private String id;
    private String key;
    private String name;
    private String assign;
    private String processId;
    /**
     * 是否多人审批
     */
    private boolean multipleApproval;
    /**
     * 其他审批人
     */
    private List<String> allAssign;
    /**
     * 共同审批人
     */
    private User user;
    private TaskProgress progress;
    private FormInfo formInfo;
    @JsonIgnore
    private Task instance;
    /**
     * 描述
     */
    private String desc;
}
