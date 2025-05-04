package tech.hljzj.activiti.register;

public interface NameProcessor {
    /**
     * 生成流程名称
     *
     * @param parameter 参数
     * @return 参数
     */
    String generateProcessName(Object parameter);
}
