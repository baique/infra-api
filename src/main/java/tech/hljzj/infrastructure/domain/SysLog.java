package tech.hljzj.infrastructure.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.logger.SysLogEntity;

/**
 * 操作日志 sys_log_
 * DTO实体
 *
 * @author wa
 */
@Getter
@Setter
@TableName(value = "sys_log_")
public class SysLog extends SysLogEntity {
    /**
     * 来源应用
     */
    @TableField(value = "op_app_id_")
    private String opAppId;
}