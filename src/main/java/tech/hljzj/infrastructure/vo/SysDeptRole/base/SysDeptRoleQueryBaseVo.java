package tech.hljzj.infrastructure.vo.SysDeptRole.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.pojo.form.PageDomain;
import tech.hljzj.infrastructure.domain.SysDeptRole;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

/**
 * 部门关联角色 sys_dept_role_ 
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysDeptRoleQueryBaseVo<T extends SysDeptRole> extends PageDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    /** id_     */
    private String id,idNot,idLike,idPrefix,idSuffix;
    private List<String> idIn,idNotIn;


    
    public Consumer<LambdaQueryWrapper<? extends T>> conditionId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getId()),SysDeptRole::getId, StrUtil.trim(this.getId()));
          builder.ne(StrUtil.isNotBlank(this.getIdNot()),T::getId, StrUtil.trim(this.getIdNot()));
          builder.in(null != this.getIdIn() && this.getIdIn().size() > 0,T::getId, this.getIdIn());
          builder.notIn(null != this.getIdNotIn() && this.getIdNotIn().size() > 0,T::getId, this.getIdNotIn());
          if (StrUtil.isNotBlank(this.getIdLike())) {
              builder.like(T::getId, StrUtil.trim(this.getIdLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getIdPrefix()),T::getId, StrUtil.trim(this.getIdPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getIdSuffix()),T::getId, StrUtil.trim(this.getIdSuffix()));
          }


        };
    }

    /**
     * 默认排序
     */
    public Consumer<LambdaQueryWrapper<? extends T>> defaultSortBy() {
        return (builder) -> {
            builder.orderByDesc(T::getId);
        };
    }

    /**
     * 构建查询条件
     */
    public <R extends T> LambdaQueryWrapper<R> buildQueryWrapper() {
        LambdaQueryWrapper<R> builder = Wrappers.lambdaQuery();
        this.conditionId().accept(builder);
        this.defaultSortBy().accept(builder);
        return builder;
    }

}