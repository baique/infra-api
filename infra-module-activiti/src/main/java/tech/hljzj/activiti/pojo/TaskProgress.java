package tech.hljzj.activiti.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class TaskProgress {
    /**
     * 是否完成审批（多人审批时此处表示了当前审批任务是否全部完成）
     */
    private boolean complete;
    /**
     * 表示是因为退回才产生的完成动作
     */
    private boolean rollback;

    /**
     * 这些用户选择了通过（与拒绝人并集就是当前环节全部参与者）
     */
    private List<String> passUser;

    /**
     * 这些用户选择拒绝（与通过人并集就是当前环节全部参与者）
     */
    private List<String> denyUser;
}
