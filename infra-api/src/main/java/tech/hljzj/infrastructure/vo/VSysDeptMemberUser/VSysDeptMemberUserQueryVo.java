package tech.hljzj.infrastructure.vo.VSysDeptMemberUser;

import cn.hutool.core.util.StrUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.VSysDeptMemberUser;
import tech.hljzj.infrastructure.vo.VSysUser.base.VSysUserQueryBaseVo;

import java.util.Objects;
import java.util.function.Consumer;

@Getter
@Setter
public class VSysDeptMemberUserQueryVo extends VSysUserQueryBaseVo<VSysDeptMemberUser> {
    private static final long serialVersionUID = 1L;
    /**
     * 这里可以让用户自行组合
     */
    private Integer ownerType;

    /**
     * 主属部门标识（此条件内含ownerType = 1)
     */
    private String mainFollowDeptId;

    /**
     * 兼属部门标识（此条件内含ownerType = 2)
     */
    private String followDeptId;

    public <T extends VSysDeptMemberUser> Consumer<MPJLambdaWrapper<T>> conditionMainFollowDeptId() {
        return (builder) -> {
            if (StrUtil.isNotBlank(this.getMainFollowDeptId())) {
                builder.and(ad -> {
                    ad.eq(T::getDeptId, StrUtil.trim(this.getMainFollowDeptId()));
                    ad.eq(T::getOwnerType, 1);
                });
            }
        };
    }

    public <T extends VSysDeptMemberUser> Consumer<MPJLambdaWrapper<T>> conditionFollowDeptId() {
        return (builder) -> {
            if (StrUtil.isNotBlank(this.getFollowDeptId())) {
                builder.and(ad -> {
                    ad.eq(T::getDeptId, StrUtil.trim(this.getFollowDeptId()));
                    ad.eq(T::getOwnerType, 2);
                });
            }
        };
    }


    public <V extends VSysDeptMemberUser> MPJLambdaWrapper<V> buildDeptMemberQueryWrapper() {
        MPJLambdaWrapper<V> q = super.buildQueryWrapper();
        q.eq(Objects.nonNull(ownerType), VSysDeptMemberUser::getOwnerType, ownerType);
        this.<V>conditionMainFollowDeptId().accept(q);
        this.<V>conditionFollowDeptId().accept(q);
        return q;
    }


}
