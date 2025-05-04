package tech.hljzj.activiti.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.hljzj.activiti.manager.SimpleProcessRegister;
import tech.hljzj.activiti.pojo.User;
import tech.hljzj.activiti.register.NameProcessor;
import tech.hljzj.framework.security.bean.UserInfo;
import tech.hljzj.framework.util.web.AuthUtil;

import java.util.*;

@Intercepts({
    @Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
    )
})
@Component
public class FlowStarterInterceptor implements Interceptor {
    @Autowired
    private Registry registry;


    public void startProcess(Object parameter) {
        FlowStarter flowStarter = parameter.getClass().getAnnotation(FlowStarter.class);
        // 这里标识要启动某个流程实例
        String key = flowStarter.key();
        User user = new User();
        if (AuthUtil.isLogin()) {
            UserInfo u = AuthUtil.getLoginUser().getUserInfo();
            user.setId(u.getId());
            user.setName(u.getName());
        }
        SimpleProcessRegister d = registry.get(key);
        String name = d.processName();
        if (d instanceof NameProcessor) {
            name = StrUtil.blankToDefault(((NameProcessor) d).generateProcessName(parameter), name);
        }
        // 启动一个流程，将数据拷贝到流程实例中
        d.startProcess(
            "" + ReflectUtil.getFieldValue(parameter, flowStarter.idField()),
            name,
            user,
            BeanUtil.beanToMap(parameter)
        );
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object param = invocation.getArgs()[1];

        // 仅拦截 INSERT 操作
        boolean isInsert = ms.getSqlCommandType() == SqlCommandType.INSERT;

        // 继续执行插入
        Object result = invocation.proceed();

        // 插入完成后执行逻辑
        if (isInsert && param != null) {
            // 多种情况判断（单个实体、集合、Map 等）
            List<Object> entityList = extractEntityList(param);
            for (Object entity : entityList) {
                if (entity.getClass().isAnnotationPresent(FlowStarter.class)) {
                    // 执行自定义处理，例如记录日志、发布事件等
                    startProcess(entity);
                }
            }
        }
        return result;
    }


    private List<Object> extractEntityList(Object param) {
        if (param instanceof Collection) {
            return new ArrayList<>((Collection<?>) param);
        } else if (param instanceof Map) {
            // MyBatis 批量插入时可能用 map 包裹
            Object et = ((Map<?, ?>) param).get("et");
            if (et instanceof Collection) {
                return new ArrayList<>((Collection<?>) et);
            } else if (et != null) {
                return Collections.singletonList(et);
            }
        }
        return Collections.singletonList(param);
    }
}