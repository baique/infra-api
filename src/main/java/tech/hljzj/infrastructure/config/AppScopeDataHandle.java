package tech.hljzj.infrastructure.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.infrastructure.util.AppScopeHolder;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class AppScopeDataHandle extends DataPermissionInterceptor {
    public AppScopeDataHandle() {
        super(new AppScopeData());
    }

    @Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        super.beforeUpdate(executor, ms, parameter);
    }

    public static class AppScopeData implements MultiDataPermissionHandler {
        public static final Set<String> NAMES = new HashSet<>();

        static {
            NAMES.add("sys_role_");
            NAMES.add("sys_menu_");
        }

        @Override
        public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {
            // 在此处编写自定义数据权限逻辑
            try {
                if (NAMES.contains(table.getName())) {
                    String scopeAppId = AppScopeHolder.getScopeAppIdIfNotInfra();
                    if (StrUtil.isBlank(scopeAppId)) {
                        return null;
                    }
                    String sqlSegment = " owner_app_id_ = '" + scopeAppId + "'";
                    return CCJSqlParserUtil.parseCondExpression(sqlSegment);
                }
                return null;
            } catch (JSQLParserException e) {
                throw UserException.defaultError("检索条件改写失败", e);
            }
        }
    }
}
